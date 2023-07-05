package br.com.senac.domain.forum.controller;

import br.com.senac.domain.forum.command.AnswerQuestionCommand;
import br.com.senac.domain.forum.command.AskQuestionCommand;
import br.com.senac.domain.forum.controller.model.AnswerQuestion;
import br.com.senac.domain.forum.controller.model.AskQuestion;
import br.com.senac.domain.forum.query.FindAnswersQuery;
import br.com.senac.domain.forum.query.FindQuestionAllQuery;
import br.com.senac.domain.forum.query.FindQuestionQuery;
import br.com.senac.domain.forum.query.repository.AnswerViewRepository;
import br.com.senac.domain.forum.query.repository.QuestionViewRepository;
import br.com.senac.domain.forum.query.view.AnswerView;
import br.com.senac.domain.forum.query.view.AnswerViewModel;
import br.com.senac.domain.forum.query.view.QuestionView;
import br.com.senac.domain.forum.query.view.QuestionViewModel;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Tag(name = "Forum", description = "Manage questions")
@RestController
@RequestMapping(value = "/questions")
public class ForumController {

	private final CommandGateway commandGateway;
	private final QueryGateway queryGateway;
	private final QuestionViewRepository questionViewRepository;
	private final AnswerViewRepository answerViewRepository;

	public ForumController(CommandGateway commandGateway, QueryGateway queryGateway,QuestionViewRepository questionViewRepository, AnswerViewRepository answerViewRepository) {
		this.commandGateway = commandGateway;
		this.queryGateway = queryGateway;
		this.questionViewRepository = questionViewRepository;
		this.answerViewRepository = answerViewRepository;
	}

	@PostMapping("/create")
	public CompletableFuture<QuestionView> createAskQuestion(@RequestBody AskQuestion askQuestion) {
		Assert.notNull(askQuestion.getDescription(), "'Description' missing - please provide a description");
		Assert.notNull(askQuestion.getUserId(), "'UserId' missing - please provide a user");
		Assert.notNull(askQuestion.getChapterId(), "'ChapterId' missing - please provide a chapter");

		return commandGateway.send(new AskQuestionCommand(UUID.randomUUID(), askQuestion.getDescription(), "", UUID.fromString(askQuestion.getUserId()), UUID.fromString(askQuestion.getChapterId())));
	}

	@DeleteMapping("/{questionId}")
	public ResponseEntity deleteQuestion(@PathVariable("questionId") String questionId) {
		Assert.notNull(questionId, "'QuestionId' missing - please provide a question validated");

		try {
			questionViewRepository.deleteById(UUID.fromString(questionId));
		} catch (IllegalArgumentException ex){
			return ResponseEntity.ok(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return ResponseEntity.ok(HttpStatus.OK);
	}

	@GetMapping("/")
	public CompletableFuture<List<QuestionViewModel>> FindQuestion() {
		return queryGateway.query(new FindQuestionAllQuery(), ResponseTypes.multipleInstancesOf(QuestionViewModel.class));
	}

	@GetMapping("/{questionId}")
	public CompletableFuture<QuestionViewModel> FindQuestion(@PathVariable("questionId") String questionId) {
		Assert.notNull(questionId, "'QuestionId' missing - please provide a question validated");
		return queryGateway.query(
				new FindQuestionQuery(UUID.fromString(questionId)),
				ResponseTypes.instanceOf(QuestionViewModel.class)
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
										answerQuestion.getAnswerParents().isEmpty() ?
											new UUID( 0 , 0 )
												:UUID.fromString(answerQuestion.getAnswerParents()),
										answerQuestion.getDescription(),
										"",
										UUID.fromString(answerQuestion.getUserId()),
										LocalDateTime.now(),
										LocalDateTime.now()
				));
	}

	@DeleteMapping("/{questionId}/answers/{answerId}/{userId}")
	public ResponseEntity deleteAnswer(@PathVariable("questionId") String questionId, @PathVariable("answerId") String answerId, @PathVariable("userId") String userId) {
		Assert.notNull(questionId, "'QuestionId' missing - please provide a question");
		Assert.notNull(answerId, "'AnswerId' missing - please provide a question");
		Assert.notNull(userId, "'UserId' missing - please provide a question");

		try {
			UUID _questionId = UUID.fromString(questionId);
			UUID _answerId = UUID.fromString(answerId);

			QuestionView questionView = questionViewRepository.findById(_questionId).get();
			Assert.notNull(questionView, "'QuestionId' missing - please provide a question");

			AnswerView answerView = answerViewRepository.findById(_answerId).get();
			Assert.notNull(answerView, "'AnswerId' missing - please provide a question");

			if(!(answerView.getQuestionId().equals(questionView.getQuestionId()) && userId.equals(answerView.getUserId()))){
				return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
			}

			answerViewRepository.deleteById(_answerId);
		} catch (IllegalArgumentException ex){
			return ResponseEntity.ok(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return ResponseEntity.ok(HttpStatus.OK);
	}

	@GetMapping("/{questionId}/answers/")
	public CompletableFuture<List<AnswerViewModel>> createAnswerQuestion(@PathVariable("questionId") String questionId) {
		Assert.notNull(questionId, "'QuestionId' missing - please provide a question");
		return queryGateway.query(new FindAnswersQuery(UUID.fromString(questionId)), ResponseTypes.multipleInstancesOf(AnswerViewModel.class));
	}
}
