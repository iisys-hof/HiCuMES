package de.iisys.sysint.hicumes.manufacturingTerminalBackend.dtos;

import de.iisys.sysint.hicumes.core.entities.Note;
import de.iisys.sysint.hicumes.core.entities.enums.EMachineOccupationStatus;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class NoteDTO {

    private String noteString;
    private String userName;
    private LocalDateTime creationDateTime;

    public NoteDTO(String noteString, String userName, LocalDateTime creationDateTime) {
        this.noteString = noteString;
        this.userName = userName;
        this.creationDateTime = creationDateTime;
    }
}
