package com.bhut.timesheet_api_v2.modules.users.usecases;

import com.bhut.timesheet_api_v2.exceptions.ResourceNotFoundException;
import com.bhut.timesheet_api_v2.modules.timesheet.entities.ReleaseEntity;
import com.bhut.timesheet_api_v2.modules.timesheet.repository.ReleasesRepository;
import com.bhut.timesheet_api_v2.modules.users.entities.HoursBankEntity;
import com.bhut.timesheet_api_v2.modules.users.entities.PaymentEntity;
import com.bhut.timesheet_api_v2.modules.users.repository.HoursBankRepository;
import com.bhut.timesheet_api_v2.modules.users.repository.PaymentRepository;
import com.bhut.timesheet_api_v2.modules.users.repository.UsersRepository;
import com.bhut.timesheet_api_v2.modules.users.responses.CloseUserReportResponse;
import com.bhut.timesheet_api_v2.modules.users.responses.ReportCalendarResponse;
import com.bhut.timesheet_api_v2.modules.users.responses.ReportPayResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CloseUserReportUseCase {

    private final UsersRepository userRepository;

    private final ReleasesRepository releaseRepository;

    private final HoursBankRepository hoursBankRepository;

    private final PaymentRepository paymentRepository;

    CloseUserReportUseCase(final UsersRepository usersRepository, final ReleasesRepository releaseRepository, final HoursBankRepository hoursBankRepository, final UsersRepository userRepository, final PaymentRepository paymentRepository) {
        this.userRepository = userRepository;
        this.releaseRepository = releaseRepository;
        this.hoursBankRepository = hoursBankRepository;
        this.paymentRepository = paymentRepository;
    }

    public CloseUserReportResponse closeUserReport(String userId, int year, int month) {
        final var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<ReleaseEntity> releases = releaseRepository.findByUserIdAndYearAndMonth(userId, year, month);

        double totalHours = calculateTotalHours(releases);
        double contractTotal = user.getContractTotal();
        boolean hasBankHours = user.isHasBankHours();
        double hoursForPay = hasBankHours ? contractTotal : totalHours;
        double bankHoursBalance = hasBankHours ? totalHours - contractTotal : 0;

        if (bankHoursBalance != 0) {
            HoursBankEntity bankEntity = HoursBankEntity.builder()
                    .userId(userId)
                    .date(LocalDate.of(year, month, 1))
                    .hour(String.valueOf(bankHoursBalance))
                    .description("SALDO DE HORAS DO " + String.format("%02d", month) + "/" + year)
                    .build();
            hoursBankRepository.save(bankEntity);
        }

        CloseUserReportResponse response = new CloseUserReportResponse();
        response.setName(user.getName());
        response.setTeam(user.getTeam());
        response.setBankHours(hasBankHours);
        response.setCalendar(new ReportCalendarResponse(String.valueOf(month), String.valueOf(year), String.valueOf(totalHours)));
        response.setPay(new ReportPayResponse(LocalDate.now(), hoursForPay, String.valueOf(totalHours)));


        final var paymentEntity = PaymentEntity.builder()
                .userId(userId)
                .month(month)
                .totalHours(bankHoursBalance)
                .paymentDate(LocalDate.now())
                .hourValue(user.getHourValue())
                .totalValue(bankHoursBalance * user.getHourValue().doubleValue())
                .currentHourBank(bankHoursBalance)
                .build();
        paymentRepository.save(paymentEntity);
        return response;
    }

    private double calculateTotalHours(List<ReleaseEntity> releases) {
        return releases.stream()
                .mapToDouble(release -> Double.parseDouble(release.getTotal()))
                .sum();
    }
}
