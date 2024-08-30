package com.personalize.userservice.service;

import com.personalize.common.entity.R;
import com.personalize.userservice.entity.NoteDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface INoteService {
    R<Boolean> updateUserNote(NoteDTO noteDTO, String authorization);
    R<NoteDTO> getUserNote(String knowledgeId, String authorization);
    R<Boolean> createUserNote(NoteDTO noteDTO,String authorization);

    R<List<NoteDTO>> getAllNotes(String authorization);
}
