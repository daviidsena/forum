package br.com.senac.domain.forum.aggregate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.senac.domain.forum.command.DisableQuestionCommand;
import br.com.senac.domain.forum.event.QuestionDisabledEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import br.com.senac.domain.forum.command.AskQuestionCommand;
import br.com.senac.domain.forum.event.QuestionCreatedEvent;

@Aggregate
public class Question {

	@AggregateIdentifier
	private UUID questionId;
	private String description;
	private String status;
	private UUID userId;
	private UUID chapterId;
	private List<UUID> selectedAnswers;

	@CommandHandler
	public Question(AskQuestionCommand command) {
		AggregateLifecycle.apply(new QuestionCreatedEvent(
													command.getQuestionId(),
													command.getDescription(),
													command.getStatus(),
													command.getUserId(),
													command.getChapterId(),
													0,0));
	}

	@EventSourcingHandler
	public void on(QuestionCreatedEvent event) {
		questionId = event.getQuestionId();
		description = event.getDescription();
		status = event.getStatus();
		userId = event.getUserId();
		chapterId = event.getChapterId();
		selectedAnswers = new ArrayList<>();
	}

	@CommandHandler
	public void Handler(DisableQuestionCommand command) {
		AggregateLifecycle.apply(new QuestionDisabledEvent(
				command.getQuestionId()));
	}

	@EventSourcingHandler
	public void on(QuestionDisabledEvent event) {
		questionId = UUID.randomUUID();
		description = "";
		status = "";
		userId = UUID.randomUUID();
		chapterId = UUID.randomUUID();
		selectedAnswers = new ArrayList<>();
	}
}
