package br.com.senac.domain.forum.aggregate;

import br.com.senac.domain.forum.command.TagCommand;
import br.com.senac.domain.forum.event.TagCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

@Aggregate
public class Tag {

	@AggregateIdentifier
	private UUID tagId;
	private String name;
	private String description;
	private String status;
	private UUID userId;

	@CommandHandler
	public Tag(TagCommand command) {
		AggregateLifecycle.apply(new TagCreatedEvent(
												command.getTagId(),
												command.getName(),
												command.getDescription(),
												command.getStatus(),
												command.getUserId()));
	}

	@EventSourcingHandler
	public void on(TagCreatedEvent event) {
		tagId = event.getTagId();
		name = event.getName();
		description = event.getDescription();
		status = event.getStatus();
		userId = event.getUserId();
	}
}
