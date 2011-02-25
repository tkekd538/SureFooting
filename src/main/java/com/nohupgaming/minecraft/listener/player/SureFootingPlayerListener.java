package com.nohupgaming.minecraft.listener.player;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.nohupgaming.minecraft.SureFooting;

public class SureFootingPlayerListener extends PlayerListener 
{
    private final SureFooting _plugin;
    
    public SureFootingPlayerListener(final SureFooting plugin)
    {
        _plugin = plugin;
    }
    
    @Override
    public void onPlayerMove(PlayerMoveEvent event) 
    {
        Player p = event.getPlayer();
        Block goingTo = event.getTo().getBlock();
        Block comingFrom = event.getFrom().getBlock(); 

        if (_plugin.isActive(p) && isMoving(goingTo, comingFrom))
        {
            Block lookingAt = p.getTargetBlock(null, 10);
            Block myFeet = goingTo.getRelative(0, -1, 0);
            
            int modifier = -1;
            if (lookingAt.getLocation().equals(myFeet.getLocation()) &&
                goingTo.getY() == comingFrom.getY())
            {
                modifier--;
            }
            

            // Build surrounding grid
            List<Block> platform = new ArrayList<Block>();                
            platform.add(goingTo.getRelative(0, modifier, 0)); // Ground zero
            platform.add(goingTo.getRelative(-1, modifier, 1)); //North-west
            platform.add(goingTo.getRelative(-1, modifier, 0)); //North
            platform.add(goingTo.getRelative(-1, modifier, -1)); //North-east
            platform.add(goingTo.getRelative(0, modifier, -1)); // East
            platform.add(goingTo.getRelative(1, modifier, -1)); // South-east
            platform.add(goingTo.getRelative(1, modifier, 0)); // South
            platform.add(goingTo.getRelative(1, modifier, 1)); // South-west
            platform.add(goingTo.getRelative(0, modifier, 1)); // West
            
            _plugin.clearBlocks(p);         
            List<Block> built = new ArrayList<Block>();
            
            for (Block b : platform)
            {
                if (b.getType().equals(Material.AIR))
                {
                    b.setType(Material.GLASS);
                    built.add(b);
                } 
            }
            
            _plugin.addBlocks(p, built);
        }
    }
    
    private boolean isMoving(Block to, Block from)
    {
        return (to.getX() != from.getX() ||
               to.getY() != from.getY() ||
               to.getZ() != from.getZ()); 
    }
    
}
