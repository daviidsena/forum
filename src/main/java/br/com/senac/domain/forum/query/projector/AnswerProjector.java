package br.com.senac.domain.forum.query.projector;

import br.com.senac.domain.forum.event.AnswerQuestionEvent;
import br.com.senac.domain.forum.query.FindAnswersAllQuery;
import br.com.senac.domain.forum.query.FindAnswersQuery;
import br.com.senac.domain.forum.query.repository.AnswerViewRepository;
import br.com.senac.domain.forum.query.view.AnswerView;
import br.com.senac.domain.forum.query.view.AnswerViewModel;
import br.com.senac.domain.forum.query.view.QuestionView;
import br.com.senac.domain.forum.query.view.QuestionViewModel;
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
public class AnswerProjector {
	private final AnswerViewRepository answerViewRepository;

	public AnswerProjector(AnswerViewRepository answerViewRepository) {
		this.answerViewRepository = answerViewRepository;
	}

	@EventHandler
	public void on(AnswerQuestionEvent event) {
		AnswerView answerView = new AnswerView(
				event.getQuestionId(),
				event.getAnswerId(),
				event.getAnswerParentsId(),
				event.getDescription(),
				event.getStatus(),
				event.getUserId(),
				event.getLike(),
				event.getUnlike(),
				LocalDateTime.now(),
				LocalDateTime.now()
		);

		answerViewRepository.save(answerView);
	}

	@QueryHandler
	public List<AnswerViewModel> handle(FindAnswersQuery query) {
		List<Aluno> lst = new UserEndpointService("http://academico3.rj.senac.br/api/Estudante").getAllUser();
		List<AnswerView> questions = answerViewRepository.findByQuestionId(query.getQuestionId());

		List<AnswerViewModel> lstVM = new ArrayList<>();

		for (AnswerView answerView : questions) {
			AnswerViewModel QVM = new AnswerViewModel();

			QVM.setQuestionId(answerView.getQuestionId());
			QVM.setAnswerId(answerView.getAnswerId());
			QVM.setAnswerParentsId(answerView.getAnswerParentsId());
			QVM.setDescription(answerView.getDescription());
			QVM.setStatus(answerView.getStatus());
			QVM.setUserId(answerView.getUserId());
			QVM.setLikeAnswer(answerView.getLikeAnswer());
			QVM.setUnlikeAnswer(answerView.getUnlikeAnswer());
			QVM.getCreatedAt(answerView.getCreatedAt());

			for (Aluno aluno : lst) {
				if(aluno.getUsuarioId().equals(answerView.getUserId().toString())) {
					QVM.setUserName(aluno.getUsuario().getNomeCompleto());
				}
			}

			lstVM.add(QVM);
		}

		return lstVM;
	}

	@QueryHandler
	public List<AnswerView> handle(FindAnswersAllQuery query) {
		return answerViewRepository.findAll();
	}
}
