package pacman.controllers.examples;

import java.util.ArrayList;
import pacman.controllers.Controller;
import pacman.game.Game;
import pacman.game.Constants.DM;

import static pacman.game.Constants.*;

/*
 * Pac-Man controller as part of the starter package - simply upload this file as a zip called
 * MyPacMan.zip and you will be entered into the rankings - as simple as that! Feel free to modify 
 * it or to start from scratch, using the classes supplied with the original software. Best of luck!
 * 
 * This controller utilises 3 tactics, in order of importance:
 * 1. Get away from any non-edible ghost that is in close proximity
 * 2. Go after the nearest edible ghost
 * 3. Go to the nearest pill/power pill
 */
public class StarterPacMan extends Controller<MOVE>
{	
	private static final int MIN_DISTANCE=10;	//if a ghost is this close, run away
	private static final int CHASE_DISTANCE=400;
	
	public MOVE getMove(Game game,long timeDue)
	{			
		int current=game.getPacmanCurrentNodeIndex();
		
		//Strategy 1: if any non-edible ghost is too close (less than MIN_DISTANCE), run away
		
		
		for(GHOST ghost : GHOST.values()){
			if(game.getGhostEdibleTime(ghost)==0 && game.getGhostLairTime(ghost)==0)
				if(game.getShortestPathDistance(current,game.getGhostCurrentNodeIndex(ghost))<MIN_DISTANCE)
					return game.getNextMoveAwayFromTarget(game.getPacmanCurrentNodeIndex(),game.getGhostCurrentNodeIndex(ghost),DM.PATH);
			
			
			
			if(game.isGhostEdible(ghost)==true && game.getGhostLairTime(ghost)==0){
				if(game.getShortestPathDistance(current,game.getGhostCurrentNodeIndex(ghost))<CHASE_DISTANCE)
					return game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(),game.getGhostCurrentNodeIndex(ghost),DM.PATH);
			}
			
		}
		
		//Strategy 2: go after the pills and power pills
		
			if(game.getNumberOfActivePowerPills()>0 && game.getCurrentLevelTime() > 100){
		
				int[] powerPills=game.getPowerPillIndices();		
				
				ArrayList<Integer> powertargets=new ArrayList<Integer>();
				
				for(int i=0;i<powerPills.length;i++)			//check with power pills are available
					if(game.isPowerPillStillAvailable(i))
						powertargets.add(powerPills[i]);				
				
				int[] powertargetsArray=new int[powertargets.size()];		//convert from ArrayList to array
				
				for(int i=0;i<powertargetsArray.length;i++)
					powertargetsArray[i]=powertargets.get(i);
				
				//return the next direction once the closest target has been identified
				return game.getNextMoveTowardsTarget(current,game.getClosestNodeIndexFromNodeIndex(current,powertargetsArray,DM.PATH),DM.PATH);
		}
		
		else{
			
			int[] pills=game.getPillIndices();
			
			ArrayList<Integer> pilltargets=new ArrayList<Integer>();
			
			for(int i=0;i<pills.length;i++)					//check which pills are available			
				if(game.isPillStillAvailable(i))
					pilltargets.add(pills[i]);
						
			int[] pilltargetsArray=new int[pilltargets.size()];		//convert from ArrayList to array
			
			for(int i=0;i<pilltargetsArray.length;i++)
				pilltargetsArray[i]=pilltargets.get(i);
			
			//return the next direction once the closest target has been identified
			return game.getNextMoveTowardsTarget(current,game.getClosestNodeIndexFromNodeIndex(current,pilltargetsArray,DM.PATH),DM.PATH);
	
		}
			
	}
}























