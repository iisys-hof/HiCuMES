package de.iisys.sysint.hicumes.manufacturingTerminalBackend.dtos;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TimeRecordDTO {
    String userName;
    LocalDateTime startDateTime;
    LocalDateTime endDateTime;
    public TimeRecordDTO(LocalDateTime startDateTime, LocalDateTime endDateTime, String userName) {
        this.userName = userName;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

}
