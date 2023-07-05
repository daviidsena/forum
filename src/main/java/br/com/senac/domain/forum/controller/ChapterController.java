package br.com.senac.domain.forum.controller;

import br.com.senac.domain.forum.command.CreateChapterCommand;
import br.com.senac.domain.forum.command.UpdateChapterCommand;
import br.com.senac.domain.forum.command.DeleteChapterCommand;
import br.com.senac.domain.forum.controller.model.Chapter;
import br.com.senac.domain.forum.query.FindChapterAllQuery;
import br.com.senac.domain.forum.query.FindChapterQuery;
import br.com.senac.domain.forum.query.repository.ChapterViewRepository;
import br.com.senac.domain.forum.query.view.ChapterView;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@io.swagger.v3.oas.annotations.tags.Tag(name = "Chapter", description = "Manage chapters")
@RestController
@RequestMapping("/chapter")
public class ChapterController {
    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;
    private final ChapterViewRepository chapterViewRepository;

    public ChapterController(CommandGateway commandGateway, QueryGateway queryGateway, ChapterViewRepository chapterViewRepository) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
        this.chapterViewRepository = chapterViewRepository;
    }

    @PostMapping("/create")
    public CompletableFuture<ChapterView> createChapter(@RequestBody Chapter chapter) {
        Assert.notNull(chapter.getName(), "'Description' missing - please provide a name");
        Assert.hasText(chapter.getDescription(), "'Description' missing - please provide a description");
        Assert.notNull(chapter.getStatus(), "'Status' missing - please provide a status");

        return commandGateway.send(new CreateChapterCommand(UUID.randomUUID(), chapter.getName(), chapter.getDescription(), chapter.getStatus()));
    }

    @PostMapping("/{chapterId}")
    public CompletableFuture<ChapterView> updateChapter(@PathVariable("chapterId") String chapterId, @RequestBody Chapter chapter) {
        Assert.notNull(chapterId, "'QuestionId' missing - please provide a question");
        Assert.notNull(chapter.getName(), "'Description' missing - please provide a name");
        Assert.hasText(chapter.getDescription(), "'Description' missing - please provide a description");
        Assert.notNull(chapter.getStatus(), "'Status' missing - please provide a status");

        return commandGateway.send(new UpdateChapterCommand(UUID.fromString(chapterId), chapter.getName(), chapter.getDescription(), chapter.getStatus()));
    }

    @DeleteMapping("/{chapterId}")
    public ResponseEntity deleteChapter(@PathVariable("chapterId") String chapterId) {
        Assert.notNull(chapterId, "'QuestionId' missing - please provide a question");

        try {
            chapterViewRepository.deleteById(UUID.fromString(chapterId));
        } catch (IllegalArgumentException ex){
            return ResponseEntity.ok(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{chapterId}")
    public CompletableFuture<List<ChapterView>> findChapterById(@PathVariable("chapterId") String chapterId) {
        Assert.notNull(chapterId, "'QuestionId' missing - please provide a question validated");
        return queryGateway.query(new FindChapterQuery(UUID.fromString(chapterId)), ResponseTypes.multipleInstancesOf(ChapterView.class));
    }

    @GetMapping("/")
    public CompletableFuture<List<ChapterView>> FindChapterAll() {
        return queryGateway.query(new FindChapterAllQuery(), ResponseTypes.multipleInstancesOf(ChapterView.class));
    }
}
