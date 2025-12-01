package com.example.mobilea1.Combat;

import com.example.mobilea1.Entities.CharacterEntity;
import com.example.mobilea1.Entities.PlayerCharacter;
import com.example.mobilea1.mgp2dCore.GameEntity;

import java.util.Vector;

public class TurnBaseSystem {

    public enum GameState {
        PLAYER_TURN,
        ENEMY_TURN,
        RESOLVING,
        GAME_OVER
    }

    public GameState getState() {
        return state;
    }
    private GameState state = GameState.PLAYER_TURN;
    private Vector<CharacterEntity> characterEntities;
    private int index = -1;   // start before 0 so first call goes to 0

    public TurnBaseSystem(Vector<CharacterEntity> CE) {
        this.characterEntities = CE;
        nextTurn();
    }

    private void nextTurn() {
        do {
            index = (index + 1) % characterEntities.size();
        }
        while (!characterEntities.get(index).alive);

        CharacterEntity characterEntity = characterEntities.get(index);

        if (characterEntity instanceof PlayerCharacter) {
            state = GameState.PLAYER_TURN;
        } else {
            state = GameState.ENEMY_TURN;
        }

        System.out.println("Turn: " + characterEntity.name);
        characterEntity.onTurnStart();
    }

    public void actionCompleted(CharacterEntity CE) {

        if (state == GameState.GAME_OVER)
            return;

        // Enter resolution state
        state = GameState.RESOLVING;

        // Run cleanup or animations here
        resolvePostAction(CE);

        // Continue to next turn
        nextTurn();
    }

    private void resolvePostAction(CharacterEntity CE) {
        // Remove dead entities
        characterEntities.removeIf(ce -> !ce.alive);

        if (characterEntities.stream().noneMatch(characterEntity -> characterEntity.isPlayer)) {
            state = GameState.GAME_OVER;
            System.out.println("Enemies win!");
            return;
        }

        if (characterEntities.stream().noneMatch(characterEntity -> characterEntity.isEnemy)) {
            state = GameState.GAME_OVER;
            System.out.println("Players win!");
            return;
        }
    }
}
