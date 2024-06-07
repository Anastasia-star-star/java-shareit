package ru.practicum.shareit.booking.dto;

import lombok.*;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InpBookingDto {
    @NotNull
    private Long itemId;

    @NotNull()
    @FutureOrPresent(message = "Время должно быть в настоящем или будущем")
    private LocalDateTime start;

    @NotNull
    @Future(message = "Время должно быть в будущем!")
    private LocalDateTime end;
}
