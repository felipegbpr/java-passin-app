package devcontrol.com.passin.controllers;

import devcontrol.com.passin.dto.attendee.AttendeesListResponseDTO;
import devcontrol.com.passin.dto.event.EventIdDTO;
import devcontrol.com.passin.dto.event.EventRequestDTO;
import devcontrol.com.passin.dto.event.EventResponseDTO;
import devcontrol.com.passin.services.AttendeeService;
import devcontrol.com.passin.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final AttendeeService attendeeService;
    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDTO> getEvent(@PathVariable String id) {
        EventResponseDTO event = this.eventService.getEventDetail(id);
        return ResponseEntity.ok(event);
    }
    @PostMapping
    public ResponseEntity<EventIdDTO> createEvent(@RequestBody  EventRequestDTO body, UriComponentsBuilder uriComponentsBuilder) {
        EventIdDTO eventIdDTO = this.eventService.createEvent(body);

        var uri = uriComponentsBuilder.path("/events/{id}").buildAndExpand(eventIdDTO.eventId()).toUri();

        return ResponseEntity.created(uri).body(eventIdDTO);
    }

    @GetMapping("/attendees/{id}")
    public ResponseEntity<AttendeesListResponseDTO> getEventAttendees(@PathVariable String id) {
        AttendeesListResponseDTO attendeesListResponseDTO = this.attendeeService.getEventsAttendees(id);
        return ResponseEntity.ok(attendeesListResponseDTO);
    }
}
