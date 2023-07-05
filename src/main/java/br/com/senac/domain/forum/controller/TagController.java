package br.com.senac.domain.forum.controller;

import br.com.senac.domain.forum.query.FindTagAllQuery;
import br.com.senac.domain.forum.query.repository.QuestionViewRepository;
import br.com.senac.domain.forum.query.repository.TagViewRepository;
import br.com.senac.domain.forum.query.view.TagViewModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import br.com.senac.domain.forum.command.TagCommand;
import br.com.senac.domain.forum.controller.model.Tag;
import br.com.senac.domain.forum.query.view.TagView;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@io.swagger.v3.oas.annotations.tags.Tag(name = "Tag", description = "Manage tags")
@RestController
@RequestMapping("/tag")
public class TagController {
    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;
    private final TagViewRepository tagViewRepository;

    public TagController(CommandGateway commandGateway, QueryGateway queryGateway, TagViewRepository tagViewRepository) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
        this.tagViewRepository = tagViewRepository;
    }

    @PostMapping("/create")
    public CompletableFuture<TagView> createTag(@RequestBody Tag tag) {
        Assert.notNull(tag.getName(), "'Description' missing - please provide a name");
        Assert.hasText(tag.getDescription(), "'Description' missing - please provide a description");
        Assert.notNull(tag.getStatus(), "'Status' missing - please provide a status");
        Assert.hasText(tag.getUserId(), "'UserId' missing - please provide a user");

        return commandGateway.send(new TagCommand(UUID.randomUUID(), tag.getName(), tag.getDescription(), tag.getStatus(), UUID.fromString(tag.getUserId())));
    }

    @DeleteMapping("/{tagId}")
    public ResponseEntity deleteChapter(@PathVariable("tagId") String tagId) {
        Assert.notNull(tagId, "'TagId' missing - please provide a question");

        try {
            tagViewRepository.deleteById(UUID.fromString(tagId));
        } catch (IllegalArgumentException ex){
            return ResponseEntity.ok(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/")
    public CompletableFuture<List<TagViewModel>> FindTagAll() {
        return queryGateway.query(new FindTagAllQuery(), ResponseTypes.multipleInstancesOf(TagViewModel.class));
    }
}