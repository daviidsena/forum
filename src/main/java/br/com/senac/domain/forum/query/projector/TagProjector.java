package br.com.senac.domain.forum.query.projector;

import br.com.senac.domain.forum.event.TagCreatedEvent;
import br.com.senac.domain.forum.query.FindTagAllQuery;
import br.com.senac.domain.forum.query.FindTagQuery;
import br.com.senac.domain.forum.query.repository.TagViewRepository;

import br.com.senac.domain.forum.query.view.QuestionView;
import br.com.senac.domain.forum.query.view.QuestionViewModel;
import br.com.senac.domain.forum.query.view.TagView;
import br.com.senac.domain.forum.query.view.TagViewModel;
import br.com.senac.infra.externaldata.UserEndpointService;
import br.com.senac.infra.externaldata.model.Aluno;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class TagProjector {
	private final TagViewRepository tagViewRepository;

	public TagProjector(TagViewRepository tagViewRepository) {
		this.tagViewRepository = tagViewRepository;
	}

	@EventHandler
	public void on(TagCreatedEvent event) {
		TagView tagView = new TagView(
				event.getTagId(),
				event.getName(),
				event.getDescription(),
				event.getStatus(),
				event.getUserId(),
				LocalDateTime.now(),
				LocalDateTime.now());

		tagViewRepository.save(tagView);
	}

	@QueryHandler
	public TagViewModel handle(FindTagQuery query) {
		TagView tagView = tagViewRepository.findById(query.getTagId()).get();
		List<Aluno> lst = new UserEndpointService("http://academico3.rj.senac.br/api/Estudante").getAllUser();
		TagViewModel TVM = new TagViewModel();

		TVM.setTagId(tagView.getTagId());
		TVM.setName(tagView.getName());
		TVM.setDescription(tagView.getDescription());
		TVM.setUserId(tagView.getUserId());
		TVM.setStatus(tagView.getStatus());
		TVM.getCreatedAt(tagView.getCreatedAt());

		for (Aluno aluno : lst) {
			if(aluno.getUsuarioId().equals(tagView.getUserId().toString())) {
				TVM.setUserName(aluno.getUsuario().getNomeCompleto());
			}
		}

		return TVM;
	}

	@QueryHandler
	public List<TagViewModel> handle(FindTagAllQuery query) {
		List<TagView> tags = tagViewRepository.findAll();
		List<Aluno> lst = new UserEndpointService("http://academico3.rj.senac.br/api/Estudante").getAllUser();
		List<TagViewModel> lstTVM = new ArrayList<>();

		for (TagView tag : tags) {
			TagViewModel TVM = new TagViewModel();

			TVM.setTagId(tag.getTagId());
			TVM.setName(tag.getName());
			TVM.setDescription(tag.getDescription());
			TVM.setUserId(tag.getUserId());
			TVM.setStatus(tag.getStatus());

			for (Aluno aluno : lst) {
				if (aluno.getUsuarioId().equals(tag.getUserId().toString())) {
					TVM.setUserName(aluno.getUsuario().getNomeCompleto());
				}
			}

			lstTVM.add(TVM);
		}

		return lstTVM;
	}
}
