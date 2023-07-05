package br.com.senac.domain.forum.query.repository;

import br.com.senac.domain.forum.query.view.ChapterView
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

interface ChapterViewRepository: JpaRepository<ChapterView, UUID>
