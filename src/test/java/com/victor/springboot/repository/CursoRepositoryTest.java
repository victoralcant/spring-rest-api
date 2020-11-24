package com.victor.springboot.repository;

import com.victor.springboot.model.Curso;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CursoRepositoryTest {

    @Autowired
    private CursoRepository cursoRepository;

    @Test
    public void deveCarregarUmCursoAoBuscarPeloNome(){

        String nomeCurso = "HTML 5";

        Curso cursoHtml5 = this.cursoRepository.findByNome(nomeCurso);

        Assertions.assertNotNull(cursoHtml5);
        Assertions.assertEquals(nomeCurso, cursoHtml5.getNome());

    }

    @Test
    public void naoDeveCarregarUmCursoAoBuscarPeloNomeQueNaoExiste(){

        String nomeCurso = "Curso de JPA";

        Curso curso = this.cursoRepository.findByNome(nomeCurso);

        Assertions.assertNull(curso);

    }

}