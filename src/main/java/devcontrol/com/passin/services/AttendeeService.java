package devcontrol.com.passin.services;

import devcontrol.com.passin.domain.attendee.Attendee;
import devcontrol.com.passin.domain.checkin.CheckIn;
import devcontrol.com.passin.dto.attendee.AttendeeDetails;
import devcontrol.com.passin.dto.attendee.AttendeesListResponseDTO;
import devcontrol.com.passin.repositories.AttendeeRepository;
import devcontrol.com.passin.repositories.CheckInRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendeeService {

    private final AttendeeRepository attendeeRepository;
    private final CheckInRepository checkInRepository;

    public  List<Attendee> getAllAttendeesFromEvent(String eventId) {
        return this.attendeeRepository.findByEventId(eventId);
    }

    public AttendeesListResponseDTO getEventsAttendees(String eventId) {
        List<Attendee> attendeeList = this.getAllAttendeesFromEvent(eventId);

        List<AttendeeDetails> attendeeDetailsList = attendeeList.stream().map(attendee -> {
          Optional<CheckIn> checkIn = this.checkInRepository.findByAttendeeId(attendee.getId());
          LocalDateTime checkedInAt = checkIn.<LocalDateTime>map(CheckIn::getCreatedAt).orElse(null);
          return new AttendeeDetails(attendee.getId(), attendee.getName(), attendee.getEmail(), attendee.getCreatedAt(), checkedInAt);

        }).toList();

        return new AttendeesListResponseDTO(attendeeDetailsList);
    }

}
