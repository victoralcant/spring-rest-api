package com.victor.springboot.api.controller;

import com.victor.springboot.api.controller.dto.DetalhesDoTopicoDto;
import com.victor.springboot.api.controller.dto.TopicoDto;
import com.victor.springboot.api.controller.dto.TopicoInput;
import com.victor.springboot.api.controller.dto.UpdateTopicoInput;
import com.victor.springboot.model.Topico;
import com.victor.springboot.repository.CursoRepository;
import com.victor.springboot.repository.TopicoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

    private final TopicoRepository topicoRepository;
    private final CursoRepository cursoRepository;

    public TopicosController(TopicoRepository topicoRepository, CursoRepository cursoRepository) {
        this.topicoRepository = topicoRepository;
        this.cursoRepository = cursoRepository;
    }

    @GetMapping
    public List<TopicoDto> list(String nomeCurso) {
        if (nomeCurso == null) {
            var topicos = topicoRepository.findAll();
            return TopicoDto.converter(topicos);
        } else {
            var topicos = topicoRepository.findByCursoNome(nomeCurso);
            return TopicoDto.converter(topicos);
        }
    }

    @PostMapping
    @Transactional
    public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoInput topicoInput, UriComponentsBuilder uriBuilder) {
        Topico topico = topicoInput.converter(cursoRepository);
        this.topicoRepository.save(topico);

        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDto(topico));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesDoTopicoDto> detalhar(@PathVariable Long id) {
        Optional<Topico> topicoOptional = topicoRepository.findById(id);
        if (topicoOptional.isPresent()){
            return ResponseEntity.ok(new DetalhesDoTopicoDto(topicoOptional.get()));
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid UpdateTopicoInput input) {
        Topico atualizado = input.atualizar(id, topicoRepository);
        return ResponseEntity.ok(new TopicoDto(atualizado));
    }

    @DeleteMapping("/id")
    @Transactional
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        topicoRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
