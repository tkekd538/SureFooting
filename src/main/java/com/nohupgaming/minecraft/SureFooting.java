package com.nohupgaming.minecraft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.nohupgaming.minecraft.listener.block.SureFootingBlockListener;
import com.nohupgaming.minecraft.listener.player.SureFootingPlayerListener;

public class SureFooting extends JavaPlugin 
{
    SureFootingPlayerListener _pl;
    SureFootingBlockListener _bl;
    
    List<Player> _players;
    HashMap<Player, List<Block>> _blocks;
    
    public SureFooting()
    {
        _pl = new SureFootingPlayerListener(this);
        _bl = new SureFootingBlockListener(this);

        _players = new ArrayList<Player>();
        _blocks = new HashMap<Player, List<Block>>();
    }
    
    @Override
    public void onDisable() 
    {
        for (Player p : _players)
        {
            clearBlocks(p);
        }
        System.out.println("SureFooting has been disabled.");
    }

    @Override
    public void onEnable() 
    {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Type.PLAYER_MOVE, _pl, Priority.Normal, this);
        pm.registerEvent(Type.BLOCK_BREAK, _bl, Priority.High, this);
        System.out.println("SureFooting has been enabled.");
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd,
        String commandLabel, String[] args) 
    {
        String cmdName = cmd.getName();
        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (cmdName.equals("surefeet"))
            {
                if (_players.contains(p))
                {
                    clearBlocks(p);
                    _players.remove(p);
                    System.out.println(p.getName() + " has turned off SureFooting");
                    p.sendMessage("You are clumsy once again, watch your step...");
                }
                else
                {
                    System.out.println(p.getName() + " has turned on SureFooting");
                    _players.add(p);
                    p.sendMessage("Your feet are sure!");
                }
                
                return true;
            }
        }
        
        return false;
    }
    
    public boolean isActive(Player p)
    {
        return _players.contains(p);
    }
    
    public void addBlocks(Player p, List<Block> b)
    {
        _blocks.remove(p);
        _blocks.put(p, b);
    }
    
    public void addBlock(Player p, Block b)
    {
        List<Block> l = _blocks.get(p);
        
        if (l == null)
        {
            l = new ArrayList<Block>();
            _blocks.put(p, l);
        }
        
        l.add(b);
    }
    
    public List<Block> getBlocks(Player p)
    {
        List<Block> l = _blocks.get(p);
        
        if (l == null)
        {
            l = new ArrayList<Block>();
        }
        
        return l;
    }
    
    public void clearBlocks(Player p)
    {
        for (Block b : getBlocks(p))
        {
            b.setType(Material.AIR);
        }
        _blocks.remove(p);
    }
}
