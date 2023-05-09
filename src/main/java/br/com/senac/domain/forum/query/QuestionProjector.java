package br.com.senac.domain.forum.query;

import br.com.senac.domain.forum.query.repository.QuestionViewRepository;
import br.com.senac.domain.forum.query.view.QuestionView;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import br.com.senac.domain.forum.event.QuestionCreatedEvent;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Component
public class QuestionProjector {
	private final QuestionViewRepository questionViewRepository;

	public QuestionProjector(QuestionViewRepository questionViewRepository) {
		this.questionViewRepository = questionViewRepository;
	}

	@EventHandler
	public void on(QuestionCreatedEvent event) {
		QuestionView questionView = new QuestionView(
				event.getQuestionId(),
				new ArrayList<UUID>(),
				event.getDescription(),
				event.getStatus(),
				event.getUserId(),
				event.getChapterId());

		questionViewRepository.save(questionView);
	}

	@QueryHandler
	public QuestionView handle(FindQuestionQuery query) {
		Optional<QuestionView> test = Optional.ofNullable(questionViewRepository.findById(query.getQuestionId()).orElse(null));
		QuestionView item = test.get();

		return item;
	}
}
