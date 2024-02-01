package idusw.leafton.model.service;

import idusw.leafton.model.DTO.StyleDTO;

import java.util.List;

public interface StyleService {
    public StyleDTO getById(Long styleId);

    public List<StyleDTO> getAll();
}