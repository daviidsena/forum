package br.com.senac.domain.forum.query.projector;

import br.com.senac.domain.forum.event.ChapterCreatedEvent;
import br.com.senac.domain.forum.query.FindChapterAllQuery;
import br.com.senac.domain.forum.query.FindChapterQuery;
import br.com.senac.domain.forum.query.repository.ChapterViewRepository;
import br.com.senac.domain.forum.query.view.ChapterView;
import br.com.senac.infra.externaldata.UserEndpointService;
import br.com.senac.infra.externaldata.model.Aluno;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class ChapterProjector {
	private final ChapterViewRepository chapterViewRepository;

	public ChapterProjector(ChapterViewRepository chapterViewRepository) {
		this.chapterViewRepository = chapterViewRepository;
	}

	@EventHandler
	public void on(ChapterCreatedEvent event) {
		ChapterView chapterView = new ChapterView(
				event.getChapterId(),
				event.getName(),
				event.getDescription(),
				event.getStatus(),
				LocalDateTime.now(),
				LocalDateTime.now());

		chapterViewRepository.save(chapterView);
	}

	@QueryHandler
	public ChapterView handle(FindChapterQuery query) {
		return Optional.ofNullable(chapterViewRepository.findById(query.getChapterId()).orElse(null)).get();
	}

	@QueryHandler
	public List<ChapterView> handle(FindChapterAllQuery query) {
		List<Aluno> lst = new UserEndpointService("http://academico3.rj.senac.br/api/Estudante").getAllUser();
		return chapterViewRepository.findAll();
	}
}
