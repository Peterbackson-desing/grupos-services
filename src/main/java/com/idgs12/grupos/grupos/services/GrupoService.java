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

    // Ver grupo con sus alumnos
    public GrupoResponseDTO findByIdWithAlumnos(int grupoId) {
        // Buscar el grupo
        GruposEntity grupo = grupoRepository.findById(grupoId).orElse(null);

        if (grupo == null) {
            return null;
        }

        GrupoResponseDTO response = new GrupoResponseDTO();
        response.setId(grupo.getId());
        response.setNombre(grupo.getNombre());
        response.setCuatrimestre(grupo.getCuatrimestre());
        response.setEstado(grupo.getEstado());

        // Obtener relaciones grupo-usuario
        List<GrupoUsuario> grupoUsuarios = grupoUsuarioRepository.findByGrupo_Id(grupoId);

        System.out.println("Grupo ID: " + grupoId);
        System.out.println("Relaciones encontradas: " + grupoUsuarios.size());

        // Obtener info alumnos
        List<UsuarioDTO> alumnos = grupoUsuarios.stream()
                .map(gu -> {
                    try {
                        System.out.println("Buscando usuario ID: " + gu.getUsuarioId());
                        UsuarioDTO usuario = usuarioFeignClient.getUsuarioById(gu.getUsuarioId());
                        System.out.println("Usuario encontrado: " + usuario.getNombre());
                        return usuario;
                    } catch (Exception e) {
                        System.err.println("Error al obtener usuario: " + e.getMessage());
                        return null;
                    }
                })
                .filter(u -> u != null)
                .collect(Collectors.toList());

        response.setAlumnos(alumnos);

        return response;
    }

    @Transactional
    public GruposEntity crearGrupo(GrupoDTO grupoDTO) {
        GruposEntity grupo = new GruposEntity();
        grupo.setNombre(grupoDTO.getNombre());
        grupo.setCuatrimestre(grupoDTO.getCuatrimestre());
        grupo.setEstado(grupoDTO.getEstado());
        return grupoRepository.save(grupo);
    }
    //Funcionalidad de habilitar -- Maria Fernanda Rosas Briones IDGS12
    @Transactional
    public boolean habilitarGrupo(Integer id) {
        GruposEntity grupo = grupoRepository.findById(id).orElse(null);

    // Actualizar grupo
    @Transactional
    public GruposEntity actualizarGrupo(GrupoDTO grupoDTO) {
        GruposEntity grupo = grupoRepository.findById(grupoDTO.getId()).orElse(new GruposEntity());
        grupo.setNombre(grupoDTO.getNombre());
        grupo.setCuatrimestre(grupoDTO.getCuatrimestre());
        grupo.setEstado(grupoDTO.getEstado());
        return grupoRepository.save(grupo);
    }

        if (grupo == null) {
            return false; 
        }

        if (Boolean.TRUE.equals(grupo.getEstado())) {
            return false;
        }

        grupo.setEstado(true);
        grupoRepository.save(grupo);
        return true;
    }
}
