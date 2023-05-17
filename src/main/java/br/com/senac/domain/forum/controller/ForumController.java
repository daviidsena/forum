package br.com.senac.domain.forum.controller;

import br.com.senac.domain.forum.command.AnswerQuestionCommand;
import br.com.senac.domain.forum.command.AskQuestionCommand;
import br.com.senac.domain.forum.command.DisableQuestionCommand;
import br.com.senac.domain.forum.controller.model.AnswerQuestion;
import br.com.senac.domain.forum.controller.model.AskQuestion;
import br.com.senac.domain.forum.query.FindQuestionAllQuery;
import br.com.senac.domain.forum.query.FindQuestionQuery;
import br.com.senac.domain.forum.query.view.AnswerView;
import br.com.senac.domain.forum.query.view.QuestionView;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/questions")
public class ForumController {

	private final CommandGateway commandGateway;
	private final QueryGateway queryGateway;

	public ForumController(CommandGateway commandGateway, QueryGateway queryGateway) {
		this.commandGateway = commandGateway;
		this.queryGateway = queryGateway;
	}

	@PostMapping("/create")
	public CompletableFuture<QuestionView> createAskQuestion(@RequestBody AskQuestion askQuestion) {
		Assert.notNull(askQuestion.getDescription(), "'Description' missing - please provide a description");
		Assert.notNull(askQuestion.getUserId(), "'UserId' missing - please provide a user");
		Assert.notNull(askQuestion.getChapterId(), "'ChapterId' missing - please provide a chapter");
		return commandGateway.send(new AskQuestionCommand(UUID.randomUUID(), askQuestion.getDescription(), "", UUID.fromString(askQuestion.getUserId()), UUID.fromString(askQuestion.getChapterId())));
	}

	@DeleteMapping("/{questionId}")
	public CompletableFuture<Boolean> deleteQuestion(@PathVariable("questionId") String questionId) {
		Assert.notNull(questionId, "'QuestionId' missing - please provide a question validated");
		return commandGateway.send(new DisableQuestionCommand(UUID.randomUUID()));
	}

	@GetMapping("/")
	public CompletableFuture<List<QuestionView>> FindQuestion() {
		return queryGateway.query(new FindQuestionAllQuery(), ResponseTypes.multipleInstancesOf(QuestionView.class));
	}

	@GetMapping("/{questionId}")
	public CompletableFuture<QuestionView> FindQuestion(@PathVariable("questionId") String questionId) {
		Assert.notNull(questionId, "'QuestionId' missing - please provide a question validated");
		return queryGateway.query(
				new FindQuestionQuery(UUID.fromString(questionId)),
				ResponseTypes.instanceOf(QuestionView.class)
		);
	}

	@PostMapping("/{questionId}/answers/")
	public CompletableFuture<AnswerView> createAnswerQuestion(@PathVariable("questionId") String questionId, @RequestBody AnswerQuestion answerQuestion) {
		Assert.notNull(questionId, "'QuestionId' missing - please provide a question");
		Assert.notNull(answerQuestion.getDescription(), "'Description' missing - please provide a description");
		Assert.notNull(answerQuestion.getUserId(), "'UserId' missing - please provide a user");

		return commandGateway.send(new AnswerQuestionCommand(
										UUID.fromString(questionId),
										UUID.randomUUID(),
										answerQuestion.getAnswerParents() != "" ?
											UUID.fromString(answerQuestion.getAnswerParents())
											: new UUID( 0 , 0 ),
										answerQuestion.getDescription(),
										"",
										UUID.fromString(answerQuestion.getUserId())
				));
	}
}


//	@GetMapping("/ask-question/")
//	public void AskQuestion(@RequestBody AskQuestionCommand askQuestionCommand) {
//		_forum.handler(askQuestionCommand);
//	}
//
//	@GetMapping("/approve-question/")
//	public void ApproveAsk(@RequestBody ApproveQuestionCommand approveQuestionCommand) {
//		_forum.handler(approveQuestionCommand);
//	}
//
//	@GetMapping("/answer-question/")
//	public void AnswerAsk(@RequestBody AnswerQuestionCommand answerQuestionCommand) {
//		_forum.handler(answerQuestionCommand);
//	}
//
//	@GetMapping("/validate-answer/")
//	public void ValidateAskQuestion(@RequestBody ValidateAnswerCommand validateAnswerCommand) {
//		_forum.handler(validateAnswerCommand);
//	}