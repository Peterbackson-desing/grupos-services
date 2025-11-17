package com.idgs12.grupos.grupos.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idgs12.grupos.grupos.dto.GrupoDTO;
import com.idgs12.grupos.grupos.dto.GrupoResponseDTO;
import com.idgs12.grupos.grupos.dto.UsuarioDTO;
import com.idgs12.grupos.grupos.entity.GrupoUsuario;
import com.idgs12.grupos.grupos.entity.GruposEntity;
import com.idgs12.grupos.grupos.FeignClient.UsuarioFeignClient;
import com.idgs12.grupos.grupos.repository.GrupoRepository;
import com.idgs12.grupos.grupos.repository.GrupoUsuarioRepository;

@Service
public class GrupoService {

    @Autowired
    private GrupoUsuarioRepository grupoUsuarioRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private UsuarioFeignClient usuarioFeignClient;

    @Transactional
    public GruposEntity crearGrupo(GrupoDTO grupoDTO) {
        GruposEntity grupo = new GruposEntity();
        grupo.setNombre(grupoDTO.getNombre());
        grupo.setCuatrimestre(grupoDTO.getCuatrimestre());
        grupo.setEstado(grupoDTO.getEstado());
        return grupoRepository.save(grupo);
    }

}
