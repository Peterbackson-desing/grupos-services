package com.idgs12.grupos.grupos.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.idgs12.grupos.grupos.dto.GrupoDTO;
import com.idgs12.grupos.grupos.dto.GrupoListDTO;
import com.idgs12.grupos.grupos.dto.GrupoResponseDTO;
import com.idgs12.grupos.grupos.entity.GruposEntity;
import com.idgs12.grupos.grupos.repository.GrupoRepository;
import com.idgs12.grupos.grupos.repository.GrupoUsuarioRepository;
import com.idgs12.grupos.grupos.services.GrupoService;

@RestController
@RequestMapping("/grupos")
@CrossOrigin(origins = "*")
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private GrupoUsuarioRepository grupoUsuarioRepository;

    @PostMapping
    public ResponseEntity<GruposEntity> crearGrupo(@RequestBody GrupoDTO grupoDTO) {
        GruposEntity nuevoGrupo = grupoService.crearGrupo(grupoDTO);
        return ResponseEntity.ok(nuevoGrupo);
    }
}
