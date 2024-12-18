package com.application.Recipe.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.application.Recipe.CompositeKeys.InstructionId;
import com.application.Recipe.Models.Instruction;

public interface InstructionsRepository extends JpaRepository<Instruction, InstructionId>{

}
