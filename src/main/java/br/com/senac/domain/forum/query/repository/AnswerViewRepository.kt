package br.com.senac.domain.forum.query.repository;
import br.com.senac.domain.forum.query.view.AnswerView
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

interface AnswerViewRepository: JpaRepository<AnswerView, UUID>
