package com.outlook.agusgamerHD1;



import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.MemorySection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import net.minecraft.server.v1_8_R1.Item;

/**
*
* @author AgustinGomez y WilsonForero
*/
public class Principal extends JavaPlugin implements Listener{

	public static Plugin instance;
    public static ArrayList<Player> jugadores = new ArrayList<Player>();
  
  @Override
  public void onEnable()
  {
    getLogger().info("Plugin activado correctamente!");
    Bukkit.getPluginManager().registerEvents(this,this);
    instance = this;
    getConfig().options().copyDefaults(true);
    saveConfig();
  }
  
  @Override
  public void onDisable() {
      getLogger().info("Plugin Desactivado Correctamente!");
  }
  
  public static int contador = 10;
  public static boolean contadorempezado = false;
  public boolean eventoempezado=false;
  
  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      if(cmd.getName().equalsIgnoreCase("setspawnwinner")){
          Player p = (Player)sender;
          if(sender.hasPermission("Event.setspawnwinner")){
              getConfig().set("winner.x", (int)p.getLocation().getX());
              getConfig().set("winner.y", (int)p.getLocation().getY());
              getConfig().set("winner.z", (int)p.getLocation().getZ());
              getConfig().set("winner.world", p.getWorld().getName());
              saveConfig();
              reloadConfig();
              
              int x = getConfig().getInt("winner.x");
              int y = getConfig().getInt("winner.y");
              int z = getConfig().getInt("winner.z");
              String mundo = getConfig().getString("winner.world");
              p.sendMessage("§a§lHas puesto el spawn del ganador del evento en las cordenadas " + x + " " + y + " " + z + " Mundo: " + mundo );              
          }
          return true;
      }
      if(cmd.getName().equalsIgnoreCase("spawnwinner")){
          Player p = (Player)sender;
          if(sender.hasPermission("Event.spawn")){
          int x = getConfig().getInt("winner.x");
          int y = getConfig().getInt("winner.y");
          int z = getConfig().getInt("winner.z");
          String mundo = getConfig().getString("winner.world");
          Location l = new Location(Bukkit.getWorld(mundo),x,y,z);
          p.teleport(l); 
          }
          
          return true;
      }
      if(cmd.getName().equalsIgnoreCase("Eventsetspawn")){
          Player p = (Player)sender;
          if(sender.hasPermission("Event.setspawn")){
              getConfig().set("Spawn.x", (int)p.getLocation().getX());
              getConfig().set("Spawn.y", (int)p.getLocation().getY());
              getConfig().set("Spawn.z", (int)p.getLocation().getZ());
              getConfig().set("Spawn.world", p.getWorld().getName());
              saveConfig();
              reloadConfig();
              
              int x = getConfig().getInt("Spawn.x");
              int y = getConfig().getInt("Spawn.y");
              int z = getConfig().getInt("Spawn.z");
              String mundo = getConfig().getString("Spawn.world");
              p.sendMessage("§a§lHas puesto el spawn del evento en las cordenadas " + x + " " + y + " " + z + " Mundo: " + mundo );              
          }
          return true;
      }
      if(cmd.getName().equalsIgnoreCase("Eventspawn")){
          Player p = (Player)sender;
          if(sender.hasPermission("Event.spawn")){
          int x = getConfig().getInt("Spawn.x");
          int y = getConfig().getInt("Spawn.y");
          int z = getConfig().getInt("Spawn.z");
          String mundo = getConfig().getString("Spawn.world");
          Location l = new Location(Bukkit.getWorld(mundo),x,y,z);
          p.teleport(l); 
          }
          
          return true;
      }
      if(cmd.getName().equalsIgnoreCase("Event")){
          Player p = (Player)sender;
          if(sender.hasPermission("Event.eventhelp")){
          p.sendMessage("§8===================§4Eventplugs§8=====================");
          p.sendMessage("§7Pon §4/Eventstart §7para iniciar un evento.[Admins]");
          p.sendMessage("§7Pon §4/Eventsetspawn §7para seleccionar el spawn del evento.[Admins]");
          p.sendMessage("§7Pon §4/Eventspawn §7para ir al spawn del evento seleccionado.[Admins]");
          p.sendMessage("§7Pon §4/Eventaccept §7para aceptar eventos programados.");
          p.sendMessage("§8=================================================");
          }
          
          return true;
      }
      
      if(cmd.getName().equalsIgnoreCase("EventAccept")){
          Player p = (Player)sender;
          if (jugadores.contains(p)){
        	  p.sendMessage("§0[§4§lEventplugs§0]: §7Ya te encuentras registrado, en "+ contador + " segundos seras teletrasportado.");
          }else{
	          if(contador < 10){
	              jugadores.add(p);
	              p.sendMessage("§0[§4§lEventplugs§0]: §7Has entrado exitosamente al evento,en "+ contador + " segundos seras teletrasportado.");
	          }else{
	              p.sendMessage("§0[§4§lError§0]:§7 no hay un evento en el cual participar");
	              
	          }
          }
          return true;
      }
      if ((cmd.getName().equalsIgnoreCase("Eventstart")) &&
      (sender.hasPermission("Eventplugs.Eventstart")))
      {
      final Player p = (Player)sender;
      if ((!contadorempezado)&&(!eventoempezado))
      {
        
        contadorempezado = true;
        final int x = getConfig().getInt("Spawn.x");
        final int y = getConfig().getInt("Spawn.y");
        final int z = getConfig().getInt("Spawn.z");
        int xw = getConfig().getInt("winner.x");
	    int yw = getConfig().getInt("winner.y");
	    int zw = getConfig().getInt("winner.z");
        if((x!=0)&&(y!=0)&&(z!=0)&&(xw!=0)&&(yw!=0)&&(zw!=0)){
        	Bukkit.broadcastMessage("§0[§4§lEventplugs§0]: §7Evento a punto de iniciar, escribe '/Eventaccept' para ser teletransportado.");
            
	        new BukkitRunnable()
	        {
	          public void run()
	          {
	        	  
	              
		            if (contador > 0)
		            {
		              
		            contador -= 1;
		            if ((contador % 15 == 0) || ((contador < 6) && (contador != 0))) {
		                Bukkit.broadcastMessage("§0[§4§lEventplugs§0]: §7El evento Creado empezara en " + contador + " segundos." + 
		                  " pon /Eventaccept para participar");
		              }
		            }
		            else
		            {
		            	if (jugadores.size()>1){
		            
			            	eventoempezado=true;
			            	System.out.println("Empezo ya? "+eventoempezado);
			              Bukkit.broadcastMessage("§0[§4§lEventplugs§0]: §8Teletransportando a los participantes.");
			              cancel();
			              Bukkit.broadcastMessage("§0[§4§lEventplugs§0]: §8Evento iniciado! Suerte para todos!");
			              Principal.contador = 10;
			              for (int i = 0; i < Principal.jugadores.size(); i++) {
			                try
			                {
			                  Player Participantes1 = (Player)Principal.jugadores.get(i);
			                  System.out.println(Principal.jugadores);
			                  
			                  
			                  String mundo = getConfig().getString("Spawn.world");
			                  Location l = new Location(Bukkit.getWorld(mundo),x,y,z);
			                  Participantes1.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000, 1));
			                  System.out.println("Se esta tpeando a :" + l);
			                  Participantes1.teleport(l);
			                  Participantes1.setHealth(20);
			                  Participantes1.setFoodLevel(20);
			                  Participantes1.sendMessage("§8Has sido Teletransportado. Bienvenido a la arena");
			                }
			                catch (Exception e)
			                {
			                  System.out.println(e);
			                }
		              }
		              //Bukkit.getScheduler().runTaskLater(Principal.instance, new Runnable()
		              //{
		                //public void run()
		                //{
		                  contadorempezado = false;
		                  
		                //}
		              //}, 20L);
		            }else{
		            	p.sendMessage("§4§lError: §8No se ha iniciado el evento por numero insuficiente de jugadores");
		            	eventoempezado=false;
		            	this.cancel();
		            	contadorempezado = false;
		            	Principal.contador = 10;
		            }
		          }
	          
	          } 
	        }.runTaskTimer(instance, 20L, 20L);
        }else{
      	  p.sendMessage("§4§lError: §8No se hanguardado coordenadas del spawn y/o del ganador");
      	  
      	  contadorempezado = false;
        	  eventoempezado=false;
        	  contador = 10;
        }
      }
      else
      {
        p.sendMessage("§0[§4§lError§0]: §7Ya hay un evento iniciado");
      }
      return true;
    }
      
    
    return false;
  }
  @EventHandler
  public void Salirserver(PlayerQuitEvent e){
      if(jugadores.contains(e.getPlayer())){
          jugadores.remove(e.getPlayer());
      }
  }
  @EventHandler
  public void onDeath(PlayerDeathEvent event){
  	if(jugadores.size()>0){
	    	if (eventoempezado==true){
		    	System.out.println("Lo que necesito al inicio es esto "+jugadores.size());
		    	Player player = event.getEntity();
		    	if ((jugadores.contains(player))&&(jugadores.size()>=2)){
				    System.out.println("Pasa");
				    Bukkit.broadcastMessage("Testing ha sido matado por "+player.getKiller().getName());
				    Bukkit.broadcastMessage("§a "+player.getName()+"  ha sido matado por "+player.getKiller().getName());//For debug purposes
				    jugadores.remove(player);
				    int JugRestantes=jugadores.size(), Posicion=JugRestantes+1;
				    player.sendMessage("§0[§4§lEventplugs§0]: §8Has quedado en el lugar numero: §4"+Posicion+"§8. Suerte a la proxima!");
				    if(JugRestantes%2==0){
				    	Bukkit.broadcastMessage("§0[§4§lEventplugs§0]: §8Quedan "+JugRestantes+" jugadores!");
				    }
		    	}
		    	if(jugadores.size()==1){
		    		jugadores.remove(player.getKiller());
		    		
		    		eventoempezado=false;
			    	Bukkit.broadcastMessage("§aEvento terminado! Felicidades al ganador: §3"+player.getKiller().getName()+"§a!");
			    	player.getKiller().removePotionEffect(PotionEffectType.INVISIBILITY);
			        int xw = getConfig().getInt("winner.x");
			        int yw = getConfig().getInt("winner.y");
			        int zw = getConfig().getInt("winner.z");
			        String mundo = getConfig().getString("winner.world");
			        Location l = new Location(Bukkit.getWorld(mundo),xw,yw,zw);
			        player.getKiller().teleport(l);  
			        System.out.println("Estado del evento " +eventoempezado);
			        System.out.println(contadorempezado);
			        
			        int effectId = getConfig().getInt("Premio.enchant"); 
			        int enchantmentLevel = getConfig().getInt("Premio.enchantlevel");
			        int itemCode = getConfig().getInt("Premio.material");
			        
			        int effectId1 = getConfig().getInt("Premio.enchant1"); 
			        int enchantmentLevel1 = getConfig().getInt("Premio.enchantlevel1");
			        int itemCode1 = getConfig().getInt("Premio.material1");
			        
			        int effectId2 = getConfig().getInt("Premio.enchant2"); 
			        int enchantmentLevel2 = getConfig().getInt("Premio.enchantlevel2");
			        int itemCode2 = getConfig().getInt("Premio.material2");
			        
			        int effectId3 = getConfig().getInt("Premio.enchant3"); 
			        int enchantmentLevel3 = getConfig().getInt("Premio.enchantlevel3");
			        int itemCode3 = getConfig().getInt("Premio.material3");
			        
			        int effectId4 = getConfig().getInt("Premio.enchant4"); 
			        int enchantmentLevel4 = getConfig().getInt("Premio.enchantlevel4");
			        int itemCode4 = getConfig().getInt("Premio.material4");
			        
			        int effectId5 = getConfig().getInt("Premio.enchant5"); 
			        int enchantmentLevel5 = getConfig().getInt("Premio.enchantlevel5");
			        int itemCode5 = getConfig().getInt("Premio.material5");
			        
			        int effectId6 = getConfig().getInt("Premio.enchant6"); 
			        int enchantmentLevel6 = getConfig().getInt("Premio.enchantlevel6");
			        int itemCode6 = getConfig().getInt("Premio.material6");
			        
			        int effectId7 = getConfig().getInt("Premio.enchant7"); 
			        int enchantmentLevel7 = getConfig().getInt("Premio.enchantlevel7");
			        int itemCode7 = getConfig().getInt("Premio.material7");
			        
			        int effectId8 = getConfig().getInt("Premio.enchant8"); 
			        int enchantmentLevel8 = getConfig().getInt("Premio.enchantlevel8");
			        int itemCode8 = getConfig().getInt("Premio.material8");
			        
			        int effectId9 = getConfig().getInt("Premio.enchant9"); 
			        int enchantmentLevel9 = getConfig().getInt("Premio.enchantlevel9");
			        int itemCode9 = getConfig().getInt("Premio.material9");
			        
			        PlayerInventory inventario = player.getKiller().getInventory();
			        inventario.clear();
			        ItemStack myItem = new ItemStack(itemCode){};
			        Enchantment myEnchantment = new EnchantmentWrapper(effectId);  
			        myItem.addEnchantment(myEnchantment, enchantmentLevel);
			        inventario.addItem(myItem);
			        
			        PlayerInventory inventario1 = player.getKiller().getInventory();
			        
			        ItemStack myItem1 = new ItemStack(itemCode1){};
			        Enchantment myEnchantment1 = new EnchantmentWrapper(effectId1);  
			        myItem1.addEnchantment(myEnchantment1, enchantmentLevel1);
			        inventario1.addItem(myItem1);
			        
			        PlayerInventory inventario2 = player.getKiller().getInventory();
			        
			        ItemStack myItem2 = new ItemStack(itemCode2){};
			        Enchantment myEnchantment2 = new EnchantmentWrapper(effectId2);  
			        myItem2.addEnchantment(myEnchantment2, enchantmentLevel2);
			        inventario2.addItem(myItem2);
			        
			        PlayerInventory inventario3 = player.getKiller().getInventory();
			        
			        ItemStack myItem3 = new ItemStack(itemCode3){};
			        Enchantment myEnchantment3 = new EnchantmentWrapper(effectId3);  
			        myItem3.addEnchantment(myEnchantment3, enchantmentLevel3);
			        inventario3.addItem(myItem3);
			        
			        PlayerInventory inventario4 = player.getKiller().getInventory();
			        
			        ItemStack myItem4 = new ItemStack(itemCode4){};
			        Enchantment myEnchantment4 = new EnchantmentWrapper(effectId4);  
			        myItem4.addEnchantment(myEnchantment4, enchantmentLevel4);
			        inventario4.addItem(myItem4);
			        
			        PlayerInventory inventario5 = player.getKiller().getInventory();
			        
			        ItemStack myItem5 = new ItemStack(itemCode5){};
			        Enchantment myEnchantment5 = new EnchantmentWrapper(effectId5);  
			        myItem5.addEnchantment(myEnchantment5, enchantmentLevel5);
			        inventario5.addItem(myItem5);
			        
			        PlayerInventory inventario6 = player.getKiller().getInventory();
			        
			        ItemStack myItem6 = new ItemStack(itemCode6){};
			        Enchantment myEnchantment6 = new EnchantmentWrapper(effectId6);  
			        myItem6.addEnchantment(myEnchantment6, enchantmentLevel6);
			        inventario6.addItem(myItem6);
			        
			        PlayerInventory inventario7 = player.getKiller().getInventory();
			        
			        ItemStack myItem7 = new ItemStack(itemCode7){};
			        Enchantment myEnchantment7 = new EnchantmentWrapper(effectId7);  
			        myItem7.addEnchantment(myEnchantment7, enchantmentLevel7);
			        inventario7.addItem(myItem7);
			        
			        PlayerInventory inventario8 = player.getKiller().getInventory();
			        
			        ItemStack myItem8 = new ItemStack(itemCode8){};
			        Enchantment myEnchantment8 = new EnchantmentWrapper(effectId8);  
			        myItem8.addEnchantment(myEnchantment8, enchantmentLevel8);
			        inventario8.addItem(myItem8);
			        
			        PlayerInventory inventario9 = player.getKiller().getInventory();
			       
			        ItemStack myItem9 = new ItemStack(itemCode9){};
			        Enchantment myEnchantment9 = new EnchantmentWrapper(effectId9);  
			        myItem9.addEnchantment(myEnchantment9, enchantmentLevel9);
			        inventario9.addItem(myItem9);
			        
			        
			    }
	    	}
	    	
  	}
  }
}
