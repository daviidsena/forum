package br.com.senac.domain.forum.query.repository;

import br.com.senac.domain.forum.query.view.QuestionView
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

interface QuestionViewRepository: JpaRepository<QuestionView, UUID>
