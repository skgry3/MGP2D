package com.example.mobilea1.Combat;

import com.example.mobilea1.Entities.CharacterEntity;
import com.example.mobilea1.Entities.EnemyCharacter;
import com.example.mobilea1.Entities.PlayerCharacter;
import com.example.mobilea1.Outputs.TextManager;

import java.util.Vector;

public class TurnBaseSystem {
    float resolveTime = 5;
    float resolveTimer = 0;

    int turns;

    public enum GameState {
        PLAYER_TURN,
        ENEMY_TURN,
        RESOLVING,
        POST_RESOLVING,
        GAME_OVER
    }

    public GameState getState() {
        return state;
    }

    private GameState state = GameState.PLAYER_TURN;
    private final Vector<PlayerCharacter> playerCharacters = new Vector<>();
    private final Vector<EnemyCharacter> enemyCharacters = new Vector<>();
    private final Vector<CharacterEntity> orderedEntities = new Vector<>();
    private int index;   // start before 0 so first call goes to 0

    private TurnBaseSystem() {
        turns = 0;
        index = 0;
    }

    private static class BillPughSingleton {
        private static final TurnBaseSystem INSTANCE = new TurnBaseSystem();
    }

    public static TurnBaseSystem getInstance() {
        return BillPughSingleton.INSTANCE;
    }

    public void initialise(Vector<CharacterEntity> CE) {
        for (CharacterEntity ce : CE) {
            if (ce instanceof PlayerCharacter) {
                playerCharacters.add((PlayerCharacter) ce);
            } else {
                enemyCharacters.add((EnemyCharacter) ce);
            }
        }
        for (int i = 0; i < CE.size() / 2; i++) {
            orderedEntities.add(playerCharacters.get(i));
            orderedEntities.add(enemyCharacters.get(i));
        }
        index = -1;
        nextTurn();
    }

    public CharacterEntity getCurrentEntity() {
        if (orderedEntities.isEmpty())
            return null;
        return orderedEntities.get(index);
    }

    private void nextTurn() {
        if (orderedEntities.isEmpty())
            return;

        do {
            index = (index + 1) % orderedEntities.size();
        }
        while (!orderedEntities.get(index).alive);

        CharacterEntity characterEntity = orderedEntities.get(index);

        System.out.println(state.name());

        if (characterEntity.isPlayer) {
            state = GameState.PLAYER_TURN;
        } else {
            state = GameState.ENEMY_TURN;
        }
        if (TextManager.getInstance().isLoaded()) {
            TextManager.getInstance().Turns.changeText("Turn: " + characterEntity.name);
        }
        System.out.println("Turn: " + characterEntity.name);
        characterEntity.onTurnStart();
    }

    public void actionCompleted(boolean died) {

        if (state == GameState.GAME_OVER)
            return;

        if (died)
            index--;

        // Enter resolution state
        state = GameState.RESOLVING;
        TextManager.getInstance().Turns.changeText("Resolving..." );


        resolveTimer = resolveTime;

    }

    public void actionResult(float dt) {
        if (resolveTimer > 0) {
            resolveTimer -= dt;

            //if something move, add 3 sec to timer
        } else {
            state = GameState.POST_RESOLVING;
            // Run cleanup
            resolvePostAction();

            // Continue to next turn
            nextTurn();
        }
    }

    private void resolvePostAction() {

        // Remove dead entities
        orderedEntities.removeIf(ce -> !ce.alive);

        if (playerCharacters.stream().noneMatch(ce -> ce.alive)) {
            state = GameState.GAME_OVER;
            TextManager.getInstance().Results.changeText("lose....");
            TextManager.getInstance().Results.show = true;
            System.out.println("Enemies win!");
            return;
        }

        if (enemyCharacters.stream().noneMatch(ce -> ce.alive)) {
            state = GameState.GAME_OVER;
            TextManager.getInstance().Results.changeText("WIN!!!!!");
            TextManager.getInstance().Results.show = true;
            System.out.println("Players win!");
        }
    }
}
