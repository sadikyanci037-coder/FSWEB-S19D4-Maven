package com.workintech.s19d1.controller;

import com.workintech.s19d1.dto.ActorRequest;
import com.workintech.s19d1.entity.Actor;
import com.workintech.s19d1.entity.Movie;
import com.workintech.s19d1.service.ActorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/actor")
@Slf4j
public class ActorController {

    private final ActorService actorService;

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping
    public List<Actor> findAll() {
        return actorService.findAll();
    }

    @GetMapping("/{id}")
    public Actor findById(@PathVariable Long id) {
        return actorService.findById(id);
    }

    @PostMapping
    public Actor save(@RequestBody ActorRequest actorRequest) {

        Actor actor = actorRequest.getActor();

        if (actorRequest.getMovies() != null) {
            actor.setMovies(new ArrayList<>());

            for (Movie movie : actorRequest.getMovies()) {
                actor.addMovie(movie);
            }
        }

        return actorService.save(actor);
    }

    @PutMapping("/{id}")
    public Actor update(
            @PathVariable Long id,
            @RequestBody Actor actor
    ) {

        Actor existingActor = actorService.findById(id);

        existingActor.setFirstName(actor.getFirstName());
        existingActor.setLastName(actor.getLastName());
        existingActor.setGender(actor.getGender());
        existingActor.setBirthDate(actor.getBirthDate());

        if (actor.getMovies() != null) {
            existingActor.setMovies(actor.getMovies());
        }

        return actorService.save(existingActor);
    }

    @DeleteMapping("/{id}")
    public Actor delete(@PathVariable Long id) {

        Actor actor = actorService.findById(id);

        actorService.delete(actor);

        return actor;
    }
}