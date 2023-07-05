package br.com.senac.domain.forum.query.repository;

import br.com.senac.domain.forum.query.view.TagView;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

interface TagViewRepository: JpaRepository<TagView, UUID>
