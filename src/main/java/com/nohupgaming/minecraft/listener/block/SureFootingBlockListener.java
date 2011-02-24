package com.nohupgaming.minecraft.listener.block;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;

import com.nohupgaming.minecraft.SureFooting;

public class SureFootingBlockListener extends BlockListener 
{
    private final SureFooting _plugin;
    
    public SureFootingBlockListener(final SureFooting plugin)
    {
        _plugin = plugin;
    }
    
    @Override
    public void onBlockBreak(BlockBreakEvent event) 
    {
        Player p = event.getPlayer();
        
        if (_plugin.isActive(p))
        {
            Block b = event.getBlock();
            if (_plugin.getBlocks(p).contains(b))
            {
                for (Block x : _plugin.getBlocks(p))
                {
                    x.setType(Material.AIR);
                }
            }
        }
    }

}
