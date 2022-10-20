package org.frc1410.framework.flow;

import edu.wpi.first.wpilibj2.command.Command;

public interface PhaseSequencer {

	void scheduleCommand(Command command, long period, long delay);

	default void scheduleCommand(Command command) {
		scheduleCommand(command, -1L, -1L);
	}
}
