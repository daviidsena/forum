package br.com.senac.domain.forum.aggregate;

import br.com.senac.domain.forum.command.AnswerQuestionCommand;
import br.com.senac.domain.forum.event.AnswerQuestionEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.LocalDateTime;
import java.util.UUID;

@Aggregate
public class Answer {

    @AggregateIdentifier
    private UUID answerId;
    private UUID questionId;
    private UUID AnswerParents;
    private String description;
    private String status;
    private UUID userId;
    private Integer like;
    private Integer unlike;

    @CommandHandler
    public Answer(AnswerQuestionCommand command) {
        AggregateLifecycle.apply(new AnswerQuestionEvent(
                command.getQuestionId(),
                command.getAnswerId(),
                command.getAnswerParentsId(),
                command.getDescription(),
                command.getStatus(),
                command.getUserId(), 0,0,
                LocalDateTime.now(),
                LocalDateTime.now()));
    }

    @EventSourcingHandler
    public void on(AnswerQuestionEvent event) {
         answerId = event.getAnswerId();
         questionId = event.getQuestionId();
         AnswerParents = event.getAnswerParentsId();
         description = event.getDescription();
         status = event.getStatus();
         userId = event.getUserId();
         like = event.getLike();
         unlike = event.getUnlike();
    }
}
