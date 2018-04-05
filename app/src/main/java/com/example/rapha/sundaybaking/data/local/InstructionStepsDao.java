package com.example.rapha.sundaybaking.data.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.rapha.sundaybaking.data.models.InstructionStep;

import java.util.List;

@Dao
public interface InstructionStepsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertInstructionSteps(List<InstructionStep> instructionSteps);

    @Query("SELECT * FROM instruction_steps WHERE recipe_id = :recipeId")
    LiveData<List<InstructionStep>> getInstructionSteps(int recipeId);

    @Query("SELECT * FROM instruction_steps WHERE id = :id")
    LiveData<InstructionStep> getInstructionStep(int id);
}
