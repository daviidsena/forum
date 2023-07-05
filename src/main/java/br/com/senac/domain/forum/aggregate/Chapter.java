package br.com.senac.domain.forum.aggregate;

import br.com.senac.domain.forum.command.DeleteChapterCommand;
import br.com.senac.domain.forum.command.UpdateChapterCommand;
import br.com.senac.domain.forum.command.CreateChapterCommand;
import br.com.senac.domain.forum.event.*;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

@Aggregate
public class Chapter {

	@AggregateIdentifier
	private UUID chapterId;
	private String name;
	private String description;
	private String status;

	@CommandHandler
	public Chapter(CreateChapterCommand command) {
		AggregateLifecycle.apply(new ChapterCreatedEvent(
												command.getChapterId(),
												command.getName(),
												command.getDescription(),
												command.getStatus()));
	}

	@EventSourcingHandler
	public void on(ChapterCreatedEvent event) {
		chapterId = event.getChapterId();
		name = event.getName();
		description = event.getDescription();
		status = event.getStatus();
	}

	@CommandHandler
	public void Handler(UpdateChapterCommand command) {
		this.chapterId = command.getChapterId();
		this.name = command.getName();
		this.description = command.getDescription();
		this.status = command.getStatus();

		AggregateLifecycle.apply(new ChapterUpdatedEvent(
				command.getChapterId(), command.getName(), command.getDescription(), command.getStatus()));
	}

	@CommandHandler
	public void Handler(DeleteChapterCommand command) {
		AggregateLifecycle.apply(new ChapterDisabledEvent(
				command.getChapterId()));
	}

	@EventSourcingHandler
	public void on(ChapterDisabledEvent event) {
		chapterId = event.getChapterId();
	}
}
