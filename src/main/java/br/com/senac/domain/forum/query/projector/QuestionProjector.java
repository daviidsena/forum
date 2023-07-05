package br.com.senac.domain.forum.query.projector;

import br.com.senac.domain.forum.query.FindQuestionAllQuery;
import br.com.senac.domain.forum.query.FindQuestionQuery;
import br.com.senac.domain.forum.query.repository.QuestionViewRepository;
import br.com.senac.domain.forum.query.view.QuestionView;

import br.com.senac.domain.forum.query.view.QuestionViewModel;
import br.com.senac.infra.externaldata.UserEndpointService;
import br.com.senac.infra.externaldata.model.Aluno;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import br.com.senac.domain.forum.event.QuestionCreatedEvent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
				event.getChapterId(),
				LocalDateTime.now(),
				LocalDateTime.now());

		questionViewRepository.save(questionView);
	}

	@QueryHandler
	public QuestionViewModel handle(FindQuestionQuery query) {
		QuestionView question = questionViewRepository.findById(query.getQuestionId()).get();
		List<Aluno> lst = new UserEndpointService("http://academico3.rj.senac.br/api/Estudante").getAllUser();

		QuestionViewModel QVM = new QuestionViewModel();

		QVM.setQuestionId(question.getQuestionId());
		QVM.setAnswers(question.getAnswers());
		QVM.setStatus(question.getStatus());
		QVM.setDescription(question.getDescription());
		QVM.setUserId(question.getUserId());
		QVM.setChapterId(question.getChapterId());
		QVM.getCreatedAt(question.getCreatedAt());

		for (Aluno aluno : lst) {
			if(aluno.getUsuarioId().equals(question.getUserId().toString())) {
				QVM.setUserName(aluno.getUsuario().getNomeCompleto());
			}
		}

		return QVM;
	}

	@QueryHandler
	public List<QuestionViewModel> handle(FindQuestionAllQuery query) {
		List<Aluno> lst = new UserEndpointService("http://academico3.rj.senac.br/api/Estudante").getAllUser();
		List<QuestionView> questions = questionViewRepository.findAll();
		List<QuestionViewModel> lstVM = new ArrayList<>();

		for (QuestionView question : questions) {
			QuestionViewModel QVM = new QuestionViewModel();

			QVM.setQuestionId(question.getQuestionId());
			QVM.setAnswers(question.getAnswers());
			QVM.setStatus(question.getStatus());
			QVM.setDescription(question.getDescription());
			QVM.setUserId(question.getUserId());
			QVM.setChapterId(question.getChapterId());
			QVM.getCreatedAt(question.getCreatedAt());

			for (Aluno aluno : lst) {
				if(aluno.getUsuarioId().equals(question.getUserId().toString())) {
					QVM.setUserName(aluno.getUsuario().getNomeCompleto());
				}
			}

			lstVM.add(QVM);
		}

		return lstVM;
	}
}
