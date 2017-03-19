/*     */ package thaumcraft.common.lib.research;
/*     */ 
/*     */ import com.google.common.io.Files;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.CompressedStreamTools;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagInt;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraft.world.storage.IPlayerFileData;
/*     */ import net.minecraft.world.storage.ISaveHandler;
/*     */ import net.minecraft.world.storage.SaveHandler;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aspects.AspectList;
/*     */ import thaumcraft.api.internal.EnumWarpType;
/*     */ import thaumcraft.api.items.IScribeTools;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.api.research.ResearchCategories;
/*     */ import thaumcraft.api.research.ResearchCategoryList;
/*     */ import thaumcraft.api.research.ResearchHelper;
/*     */ import thaumcraft.api.research.ResearchItem;
/*     */ import thaumcraft.common.CommonProxy;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.config.Config;
/*     */ import thaumcraft.common.lib.utils.HexUtils;
/*     */ import thaumcraft.common.lib.utils.HexUtils.Hex;
/*     */ import thaumcraft.common.lib.utils.Utils;
/*     */ 
/*     */ public class ResearchManager
/*     */ {
/*     */   public static ItemStack createResearchNoteForPlayer(World world, EntityPlayer player, String key)
/*     */   {
/*  49 */     ItemStack note = null;
/*  50 */     boolean addslot = false;
/*  51 */     int slot = getResearchSlot(player, key);
/*  52 */     if (slot >= 0) {
/*  53 */       note = player.inventory.getStackInSlot(slot);
/*  54 */     } else if ((consumeInkFromPlayer(player, false)) && (player.inventory.consumeInventoryItem(net.minecraft.init.Items.paper)))
/*     */     {
/*  56 */       consumeInkFromPlayer(player, true);
/*  57 */       note = createNote(new ItemStack(ItemsTC.researchNotes), key, world);
/*     */       
/*  59 */       if (!player.inventory.addItemStackToInventory(note)) {
/*  60 */         player.dropPlayerItemWithRandomChoice(note, false);
/*     */       }
/*  62 */       player.inventoryContainer.detectAndSendChanges();
/*     */     }
/*  64 */     return note;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 120 */   static ArrayList<ResearchItem> allValidResearch = null;
/*     */   
/*     */   public static String findMatchingResearch(EntityPlayer player, Aspect aspect) {
/* 123 */     String randomMatch = null;
/*     */     
/* 125 */     if (allValidResearch == null) {
/* 126 */       allValidResearch = new ArrayList();
/*     */       
/* 128 */       Collection<ResearchCategoryList> rc = ResearchCategories.researchCategories.values();
/* 129 */       for (ResearchCategoryList cat : rc) {
/* 130 */         Collection<ResearchItem> rl = cat.research.values();
/* 131 */         for (ResearchItem ri : rl) {
/* 132 */           boolean secondary = ((ri.isSecondary()) && (Config.researchDifficulty == 0)) || (Config.researchDifficulty == -1);
/* 133 */           if ((!secondary) && (!ri.isHidden()) && (!ri.isAutoUnlock()) && (!ri.isVirtual()) && (!ri.isStub())) {
/* 134 */             allValidResearch.add(ri);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 140 */     ArrayList<String> keys = new ArrayList();
/*     */     
/* 142 */     for (ResearchItem research : allValidResearch) {
/* 143 */       if ((!isResearchComplete(player.getName(), research.key)) && (doesPlayerHaveRequisites(player.getName(), research.key)))
/*     */       {
/*     */ 
/*     */ 
/* 147 */         if (research.tags.getAmount(aspect) > 0) {
/* 148 */           keys.add(research.key);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 153 */     if (keys.size() > 0) {
/* 154 */       randomMatch = (String)keys.get(player.worldObj.rand.nextInt(keys.size()));
/*     */     }
/*     */     
/* 157 */     return randomMatch;
/*     */   }
/*     */   
/*     */   public static int getResearchSlot(EntityPlayer player, String key)
/*     */   {
/* 162 */     ItemStack[] inv = player.inventory.mainInventory;
/* 163 */     if ((inv == null) || (inv.length == 0))
/* 164 */       return -1;
/* 165 */     for (int a = 0; a < inv.length; a++) {
/* 166 */       if ((inv[a] != null) && (inv[a].getItem() != null) && (inv[a].getItem() == ItemsTC.researchNotes) && (getData(inv[a]) != null) && (getData(inv[a]).key.equals(key)))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/* 171 */         return a;
/*     */       }
/*     */     }
/* 174 */     return -1;
/*     */   }
/*     */   
/*     */   public static boolean consumeInkFromPlayer(EntityPlayer player, boolean doit) {
/* 178 */     ItemStack[] inv = player.inventory.mainInventory;
/* 179 */     for (int a = 0; a < inv.length; a++) {
/* 180 */       if ((inv[a] != null) && ((inv[a].getItem() instanceof IScribeTools)) && (inv[a].getItemDamage() < inv[a].getMaxDamage()))
/*     */       {
/*     */ 
/* 183 */         if (doit)
/* 184 */           inv[a].damageItem(1, player);
/* 185 */         return true;
/*     */       }
/*     */     }
/* 188 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean consumeInkFromTable(ItemStack stack, boolean doit)
/*     */   {
/* 193 */     if ((stack != null) && ((stack.getItem() instanceof IScribeTools)) && (stack.getItemDamage() < stack.getMaxDamage()))
/*     */     {
/* 195 */       if (doit)
/* 196 */         stack.setItemDamage(stack.getItemDamage() + 1);
/* 197 */       return true;
/*     */     }
/* 199 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean checkResearchCompletion(ItemStack contents, ResearchNoteData note, String username) {
/* 203 */     ArrayList<String> checked = new ArrayList();
/* 204 */     ArrayList<String> main = new ArrayList();
/* 205 */     ArrayList<String> remains = new ArrayList();
/* 206 */     for (HexUtils.Hex hex : note.hexes.values()) {
/* 207 */       if (((HexEntry)note.hexEntries.get(hex.toString())).type == 1) {
/* 208 */         main.add(hex.toString());
/*     */       }
/*     */     }
/* 211 */     for (HexUtils.Hex hex : note.hexes.values()) {
/* 212 */       if (((HexEntry)note.hexEntries.get(hex.toString())).type == 1) {
/* 213 */         main.remove(hex.toString());
/* 214 */         checkConnections(note, hex, checked, main, remains, username);
/* 215 */         break;
/*     */       }
/*     */     }
/*     */     
/* 219 */     if (main.size() == 0) {
/* 220 */       ArrayList<String> remove = new ArrayList();
/* 221 */       for (HexUtils.Hex hex : note.hexes.values()) {
/* 222 */         if ((((HexEntry)note.hexEntries.get(hex.toString())).type != 1) && (!remains.contains(hex.toString()))) {
/* 223 */           remove.add(hex.toString());
/*     */         }
/*     */       }
/* 226 */       for (String s : remove) {
/* 227 */         note.hexEntries.remove(s);
/* 228 */         note.hexes.remove(s);
/*     */       }
/* 230 */       note.complete = true;
/* 231 */       updateData(contents, note);
/* 232 */       return true;
/*     */     }
/* 234 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   private static void checkConnections(ResearchNoteData note, HexUtils.Hex hex, ArrayList<String> checked, ArrayList<String> main, ArrayList<String> remains, String username)
/*     */   {
/* 240 */     checked.add(hex.toString());
/* 241 */     for (int a = 0; a < 6; a++) {
/* 242 */       HexUtils.Hex target = hex.getNeighbour(a);
/* 243 */       if ((!checked.contains(target.toString())) && 
/* 244 */         (note.hexEntries.containsKey(target.toString())) && (((HexEntry)note.hexEntries.get(target.toString())).type >= 1)) {
/* 245 */         Aspect aspect1 = ((HexEntry)note.hexEntries.get(hex.toString())).aspect;
/* 246 */         Aspect aspect2 = ((HexEntry)note.hexEntries.get(target.toString())).aspect;
/* 247 */         if (((!aspect1.isPrimal()) && ((aspect1.getComponents()[0] == aspect2) || (aspect1.getComponents()[1] == aspect2))) || ((!aspect2.isPrimal()) && ((aspect2.getComponents()[0] == aspect1) || (aspect2.getComponents()[1] == aspect1))))
/*     */         {
/* 249 */           remains.add(target.toString());
/* 250 */           if (((HexEntry)note.hexEntries.get(target.toString())).type == 1) {
/* 251 */             main.remove(target.toString());
/*     */           }
/* 253 */           checkConnections(note, target, checked, main, remains, username);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static class HexEntry {
/*     */     public Aspect aspect;
/*     */     public int type;
/*     */     
/*     */     public HexEntry(Aspect aspect, int type) {
/* 264 */       this.aspect = aspect;
/* 265 */       this.type = type;
/*     */     }
/*     */   }
/*     */   
/*     */   public static ItemStack createNote(ItemStack stack, String key, World world)
/*     */   {
/* 271 */     ResearchItem rr = ResearchCategories.getResearch(key);
/* 272 */     Aspect primaryaspect = rr.getResearchPrimaryTag();
/* 273 */     AspectList aspects = new AspectList();
/*     */     
/* 275 */     if (primaryaspect == null) { return null;
/*     */     }
/* 277 */     if (stack.getTagCompound() == null) { stack.setTagCompound(new NBTTagCompound());
/*     */     }
/* 279 */     stack.getTagCompound().setString("key", key);
/* 280 */     stack.getTagCompound().setInteger("color", primaryaspect.getColor());
/* 281 */     stack.getTagCompound().setBoolean("complete", false);
/* 282 */     stack.getTagCompound().setInteger("copies", 0);
/*     */     
/*     */ 
/* 285 */     int radius = 1 + Math.min(3, rr.getComplexity());
/*     */     
/*     */ 
/* 288 */     HashMap<String, HexUtils.Hex> hexLocs = HexUtils.generateHexes(radius);
/* 289 */     ArrayList<HexUtils.Hex> outerRing = HexUtils.distributeRingRandomly(radius, rr.tags.size(), world.rand);
/*     */     
/*     */ 
/* 292 */     HashMap<String, HexEntry> hexEntries = new HashMap();
/* 293 */     HashMap<String, HexUtils.Hex> hexes = new HashMap();
/* 294 */     for (HexUtils.Hex hex : hexLocs.values()) {
/* 295 */       hexes.put(hex.toString(), hex);
/* 296 */       hexEntries.put(hex.toString(), new HexEntry(null, 0));
/*     */     }
/*     */     
/*     */ 
/* 300 */     int count = 0;
/* 301 */     for (HexUtils.Hex hex : outerRing) {
/* 302 */       hexes.put(hex.toString(), hex);
/* 303 */       hexEntries.put(hex.toString(), new HexEntry(rr.tags.getAspects()[count], 1));
/* 304 */       count++;
/*     */     }
/*     */     
/*     */ 
/* 308 */     for (Aspect aa : Aspect.getPrimalAspects()) {
/* 309 */       aspects.add(aa, rr.tags.size() + radius + world.rand.nextInt(radius));
/*     */     }
/* 311 */     aspects.writeToNBT(stack.getTagCompound(), "aspects");
/*     */     
/*     */ 
/* 314 */     if (rr.getComplexity() > 1) {
/* 315 */       int blanks = rr.getComplexity() * 2;
/* 316 */       HexUtils.Hex[] temp = (HexUtils.Hex[])hexes.values().toArray(new HexUtils.Hex[0]);
/* 317 */       while (blanks > 0) {
/* 318 */         int indx = world.rand.nextInt(temp.length);
/* 319 */         if ((hexEntries.get(temp[indx].toString()) != null) && (((HexEntry)hexEntries.get(temp[indx].toString())).type == 0))
/*     */         {
/* 321 */           boolean gtg = true;
/* 322 */           for (int n = 0; n < 6; n++) {
/* 323 */             HexUtils.Hex neighbour = temp[indx].getNeighbour(n);
/* 324 */             if ((hexes.containsKey(neighbour.toString())) && (((HexEntry)hexEntries.get(neighbour.toString())).type == 1)) {
/* 325 */               int cc = 0;
/* 326 */               for (int q = 0; q < 6; q++) {
/* 327 */                 if (hexes.containsKey(((HexUtils.Hex)hexes.get(neighbour.toString())).getNeighbour(q).toString())) {
/* 328 */                   cc++;
/*     */                 }
/* 330 */                 if (cc >= 2) break;
/*     */               }
/* 332 */               if (cc < 2) {
/* 333 */                 gtg = false;
/* 334 */                 break;
/*     */               }
/*     */             }
/*     */           }
/* 338 */           if (gtg)
/*     */           {
/* 340 */             hexes.remove(temp[indx].toString());
/* 341 */             hexEntries.remove(temp[indx].toString());
/* 342 */             temp = (HexUtils.Hex[])hexes.values().toArray(new HexUtils.Hex[0]);
/* 343 */             blanks--;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 350 */     NBTTagList gridtag = new NBTTagList();
/* 351 */     for (HexUtils.Hex hex : hexes.values()) {
/* 352 */       NBTTagCompound gt = new NBTTagCompound();
/* 353 */       gt.setByte("hexq", (byte)hex.q);
/* 354 */       gt.setByte("hexr", (byte)hex.r);
/* 355 */       gt.setByte("type", (byte)((HexEntry)hexEntries.get(hex.toString())).type);
/* 356 */       if (((HexEntry)hexEntries.get(hex.toString())).aspect != null)
/* 357 */         gt.setString("aspect", ((HexEntry)hexEntries.get(hex.toString())).aspect.getTag());
/* 358 */       gridtag.appendTag(gt);
/*     */     }
/* 360 */     stack.getTagCompound().setTag("hexgrid", gridtag);
/*     */     
/* 362 */     return stack;
/*     */   }
/*     */   
/*     */   public static ResearchNoteData getData(ItemStack stack) {
/* 366 */     if (stack == null) return null;
/* 367 */     ResearchNoteData data = new ResearchNoteData();
/*     */     
/* 369 */     if (stack.getTagCompound() == null) { return null;
/*     */     }
/* 371 */     data.key = stack.getTagCompound().getString("key");
/* 372 */     data.color = stack.getTagCompound().getInteger("color");
/* 373 */     data.complete = stack.getTagCompound().getBoolean("complete");
/* 374 */     data.copies = stack.getTagCompound().getInteger("copies");
/*     */     
/* 376 */     data.aspects.readFromNBT(stack.getTagCompound(), "aspects");
/*     */     
/* 378 */     NBTTagList grid = stack.getTagCompound().getTagList("hexgrid", 10);
/* 379 */     data.hexEntries = new HashMap();
/*     */     
/* 381 */     for (int x = 0; x < grid.tagCount(); x++) {
/* 382 */       NBTTagCompound nbt = grid.getCompoundTagAt(x);
/* 383 */       int q = nbt.getByte("hexq");
/* 384 */       int r = nbt.getByte("hexr");
/* 385 */       int type = nbt.getByte("type");
/* 386 */       String tag = nbt.getString("aspect");
/* 387 */       Aspect aspect = tag != null ? Aspect.getAspect(tag) : null;
/* 388 */       HexUtils.Hex hex = new HexUtils.Hex(q, r);
/* 389 */       data.hexEntries.put(hex.toString(), new HexEntry(aspect, type));
/* 390 */       data.hexes.put(hex.toString(), hex);
/*     */     }
/*     */     
/* 393 */     return data;
/*     */   }
/*     */   
/*     */   public static void updateData(ItemStack stack, ResearchNoteData data)
/*     */   {
/* 398 */     if (stack.getTagCompound() == null) {
/* 399 */       stack.setTagCompound(new NBTTagCompound());
/*     */     }
/*     */     
/* 402 */     stack.getTagCompound().setString("key", data.key);
/* 403 */     stack.getTagCompound().setInteger("color", data.color);
/* 404 */     stack.getTagCompound().setBoolean("complete", data.complete);
/* 405 */     stack.getTagCompound().setInteger("copies", data.copies);
/*     */     
/* 407 */     data.aspects.writeToNBT(stack.getTagCompound(), "aspects");
/*     */     
/* 409 */     NBTTagList gridtag = new NBTTagList();
/* 410 */     for (HexUtils.Hex hex : data.hexes.values()) {
/* 411 */       NBTTagCompound gt = new NBTTagCompound();
/* 412 */       gt.setByte("hexq", (byte)hex.q);
/* 413 */       gt.setByte("hexr", (byte)hex.r);
/* 414 */       gt.setByte("type", (byte)((HexEntry)data.hexEntries.get(hex.toString())).type);
/* 415 */       if (((HexEntry)data.hexEntries.get(hex.toString())).aspect != null)
/* 416 */         gt.setString("aspect", ((HexEntry)data.hexEntries.get(hex.toString())).aspect.getTag());
/* 417 */       gridtag.appendTag(gt);
/*     */     }
/* 419 */     stack.getTagCompound().setTag("hexgrid", gridtag);
/*     */   }
/*     */   
/*     */   public static boolean isResearchComplete(String playername, String key)
/*     */   {
/* 424 */     List completed = getResearchForPlayer(playername);
/* 425 */     if ((completed != null) && (completed.size() > 0)) {
/* 426 */       return completed.contains(key);
/*     */     }
/* 428 */     return false;
/*     */   }
/*     */   
/* 431 */   public static boolean loadingBlocked = false;
/*     */   private static final String RESEARCH_TAG = "THAUMCRAFT.RESEARCH";
/*     */   
/* 434 */   public static ArrayList<String> getResearchForPlayer(String playername) { ArrayList<String> out = (ArrayList)Thaumcraft.proxy.getCompletedResearch().get(playername);
/*     */     
/*     */     try
/*     */     {
/* 438 */       if ((!loadingBlocked) && (out == null) && (Thaumcraft.proxy.getClientWorld() == null) && (MinecraftServer.getServer() != null))
/*     */       {
/* 440 */         Thaumcraft.proxy.getCompletedResearch().put(playername, new ArrayList());
/*     */         
/* 442 */         if (playername != null)
/*     */         {
/* 444 */           IPlayerFileData playerNBTManagerObj = MinecraftServer.getServer().worldServerForDimension(0).getSaveHandler().getPlayerNBTManager();
/*     */           
/* 446 */           SaveHandler sh = (SaveHandler)playerNBTManagerObj;
/* 447 */           File dir = (File)net.minecraftforge.fml.common.ObfuscationReflectionHelper.getPrivateValue(SaveHandler.class, sh, new String[] { "playersDirectory", "field_75771_c", "c" });
/* 448 */           File file1 = new File(dir, "_" + playername + ".thaum");
/* 449 */           File file2 = new File(dir, "_" + playername + ".thaumbak");
/* 450 */           loadPlayerData(playername, file1, file2);
/*     */         }
/*     */         
/* 453 */         out = (ArrayList)Thaumcraft.proxy.getCompletedResearch().get(playername);
/*     */       }
/*     */     }
/*     */     catch (Exception e) {}
/*     */     
/* 458 */     if (out == null) out = new ArrayList();
/* 459 */     return out;
/*     */   }
/*     */   
/*     */   public static ArrayList<String> getResearchForPlayerSafe(String playername) {
/* 463 */     return (ArrayList)Thaumcraft.proxy.getCompletedResearch().get(playername);
/*     */   }
/*     */   
/*     */   public static HashMap<String, Byte> getResearchFlagsForPlayer(String playername) {
/* 467 */     if (!Thaumcraft.proxy.getCompletedResearchFlags().containsKey(playername))
/* 468 */       Thaumcraft.proxy.getCompletedResearchFlags().put(playername, new HashMap());
/* 469 */     return (HashMap)Thaumcraft.proxy.getCompletedResearchFlags().get(playername);
/*     */   }
/*     */   
/*     */   public static boolean doesPlayerHaveRequisites(String playername, String key) {
/* 473 */     ResearchItem ri = ResearchCategories.getResearch(key);
/* 474 */     if (ri == null) { return true;
/*     */     }
/* 476 */     boolean out = true;
/*     */     
/* 478 */     String[] parents = ri.parents;
/*     */     
/* 480 */     ArrayList<String> completed = getResearchForPlayer(playername);
/*     */     
/* 482 */     if ((parents != null) && (parents.length > 0)) {
/* 483 */       out = false;
/* 484 */       if ((completed != null) && (completed.size() > 0)) {
/* 485 */         out = true;
/* 486 */         for (String item : parents) {
/* 487 */           if (!completed.contains(item)) {
/* 488 */             return false;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 494 */     parents = ri.parentsHidden;
/* 495 */     if ((parents != null) && (parents.length > 0)) {
/* 496 */       out = false;
/* 497 */       if ((completed != null) && (completed.size() > 0)) {
/* 498 */         out = true;
/* 499 */         for (String item : parents) {
/* 500 */           if (!completed.contains(item)) {
/* 501 */             return false;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 507 */     return out;
/*     */   }
/*     */   
/*     */   public static Aspect getCombinationResult(Aspect aspect1, Aspect aspect2)
/*     */   {
/* 512 */     Collection<Aspect> aspects = Aspect.aspects.values();
/* 513 */     for (Aspect aspect : aspects) {
/* 514 */       if ((aspect.getComponents() != null) && (((aspect.getComponents()[0] == aspect1) && (aspect.getComponents()[1] == aspect2)) || ((aspect.getComponents()[0] == aspect2) && (aspect.getComponents()[1] == aspect1))))
/*     */       {
/*     */ 
/* 517 */         return aspect;
/*     */       }
/*     */     }
/* 520 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void setResearchFlags(String username, String key, byte flags)
/*     */   {
/* 532 */     getResearchFlagsForPlayer(username).put(key, Byte.valueOf(flags));
/*     */   }
/*     */   
/*     */   public static boolean hasNewResearchFlag(String username, String key) {
/* 536 */     if (getResearchFlagsForPlayer(username).containsKey(key)) {
/* 537 */       return Utils.getBit(((Byte)getResearchFlagsForPlayer(username).get(key)).byteValue(), 1);
/*     */     }
/* 539 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean hasNewPageFlag(String username, String key) {
/* 543 */     if (getResearchFlagsForPlayer(username).containsKey(key)) {
/* 544 */       return Utils.getBit(((Byte)getResearchFlagsForPlayer(username).get(key)).byteValue(), 2);
/*     */     }
/* 546 */     return false;
/*     */   }
/*     */   
/*     */   public static void setNewPageFlag(String username, String key) {
/* 550 */     if (!getResearchFlagsForPlayer(username).containsKey(key))
/* 551 */       Thaumcraft.proxy.getCompletedResearchFlags().put(username, new HashMap());
/* 552 */     if (getResearchFlagsForPlayer(username).get(key) == null) {
/* 553 */       getResearchFlagsForPlayer(username).put(key, Byte.valueOf((byte)0));
/*     */     }
/* 555 */     getResearchFlagsForPlayer(username).put(key, Byte.valueOf((byte)Utils.setBit(((Byte)getResearchFlagsForPlayer(username).get(key)).byteValue(), 2)));
/*     */   }
/*     */   
/*     */   public static void clearNewPageFlag(String username, String key) {
/* 559 */     if (!getResearchFlagsForPlayer(username).containsKey(key))
/* 560 */       Thaumcraft.proxy.getCompletedResearchFlags().put(username, new HashMap());
/* 561 */     if (getResearchFlagsForPlayer(username).get(key) == null) {
/* 562 */       getResearchFlagsForPlayer(username).put(key, Byte.valueOf((byte)0));
/*     */     }
/* 564 */     getResearchFlagsForPlayer(username).put(key, Byte.valueOf((byte)Utils.clearBit(((Byte)getResearchFlagsForPlayer(username).get(key)).byteValue(), 2)));
/*     */   }
/*     */   
/*     */   public static void setNewResearchFlag(String username, String key) {
/* 568 */     if (!getResearchFlagsForPlayer(username).containsKey(key))
/* 569 */       Thaumcraft.proxy.getCompletedResearchFlags().put(username, new HashMap());
/* 570 */     if (getResearchFlagsForPlayer(username).get(key) == null) {
/* 571 */       getResearchFlagsForPlayer(username).put(key, Byte.valueOf((byte)0));
/*     */     }
/* 573 */     getResearchFlagsForPlayer(username).put(key, Byte.valueOf((byte)Utils.setBit(((Byte)getResearchFlagsForPlayer(username).get(key)).byteValue(), 1)));
/*     */   }
/*     */   
/*     */   public static void clearNewResearchFlag(String username, String key) {
/* 577 */     if (!getResearchFlagsForPlayer(username).containsKey(key))
/* 578 */       Thaumcraft.proxy.getCompletedResearchFlags().put(username, new HashMap());
/* 579 */     if (getResearchFlagsForPlayer(username).get(key) == null) {
/* 580 */       getResearchFlagsForPlayer(username).put(key, Byte.valueOf((byte)0));
/*     */     }
/* 582 */     getResearchFlagsForPlayer(username).put(key, Byte.valueOf((byte)Utils.clearBit(((Byte)getResearchFlagsForPlayer(username).get(key)).byteValue(), 1)));
/*     */   }
/*     */   
/*     */   public static boolean completeResearchUnsaved(String username, String key, byte flags) {
/* 586 */     if (!getResearchForPlayer(username).contains(key)) {
/* 587 */       if (Thaumcraft.proxy.getCompletedResearch().get(username) == null)
/* 588 */         Thaumcraft.proxy.getCompletedResearch().put(username, new ArrayList());
/* 589 */       ((ArrayList)Thaumcraft.proxy.getCompletedResearch().get(username)).add(key);
/* 590 */       setResearchFlags(username, key, flags);
/* 591 */       return true;
/*     */     }
/* 593 */     return false;
/*     */   }
/*     */   
/*     */   public void completeResearch(EntityPlayer player, String key, byte flags) {
/* 597 */     if (completeResearchUnsaved(player.getName(), key, flags))
/*     */     {
/* 599 */       int warp = thaumcraft.api.ThaumcraftApi.getWarp(key);
/* 600 */       if ((warp > 0) && (!Config.wuss) && 
/* 601 */         (!player.worldObj.isRemote)) {
/* 602 */         if (warp > 1) {
/* 603 */           int w2 = warp / 2;
/* 604 */           if (warp - w2 > 0)
/* 605 */             ResearchHelper.addWarpToPlayer(player, warp - w2, EnumWarpType.PERMANENT);
/* 606 */           if (w2 > 0)
/* 607 */             ResearchHelper.addWarpToPlayer(player, w2, EnumWarpType.NORMAL);
/*     */         } else {
/* 609 */           ResearchHelper.addWarpToPlayer(player, warp, EnumWarpType.PERMANENT);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void loadPlayerData(String player, File file1, File file2)
/*     */   {
/*     */     try
/*     */     {
/* 621 */       NBTTagCompound data = null;
/* 622 */       if ((file1 != null) && (file1.exists())) {
/*     */         try
/*     */         {
/* 625 */           FileInputStream fileinputstream = new FileInputStream(file1);
/* 626 */           data = CompressedStreamTools.readCompressed(fileinputstream);
/* 627 */           fileinputstream.close();
/*     */         } catch (Exception e) {
/* 629 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */       
/* 633 */       if ((file1 == null) || (!file1.exists()) || (data == null) || (data.hasNoTags()))
/*     */       {
/* 635 */         Thaumcraft.log.warn("Thaumcraft data not found for " + player + ". Trying to load backup Thaumcraft data.");
/* 636 */         if ((file2 != null) && (file2.exists())) {
/*     */           try
/*     */           {
/* 639 */             FileInputStream fileinputstream = new FileInputStream(file2);
/* 640 */             data = CompressedStreamTools.readCompressed(fileinputstream);
/* 641 */             fileinputstream.close();
/*     */           } catch (Exception e) {
/* 643 */             e.printStackTrace();
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 648 */       if (data != null) {
/* 649 */         loadResearchNBT(data, player);
/*     */         
/* 651 */         if (data.hasKey("Thaumcraft.eldritch")) {
/* 652 */           int warp = data.getInteger("Thaumcraft.eldritch");
/* 653 */           Thaumcraft.proxy.getPlayerKnowledge().setWarpPerm(player, warp);
/*     */         }
/*     */         
/* 656 */         if (data.hasKey("Thaumcraft.eldritch.temp")) {
/* 657 */           Thaumcraft.proxy.getPlayerKnowledge().setWarpTemp(player, data.getInteger("Thaumcraft.eldritch.temp"));
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 662 */         if (data.hasKey("Thaumcraft.eldritch.sticky")) {
/* 663 */           Thaumcraft.proxy.getPlayerKnowledge().setWarpSticky(player, data.getInteger("Thaumcraft.eldritch.sticky"));
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 668 */         if (data.hasKey("Thaumcraft.eldritch.counter")) {
/* 669 */           Thaumcraft.proxy.getPlayerKnowledge().setWarpCounter(player, data.getInteger("Thaumcraft.eldritch.counter"));
/*     */         }
/*     */         else
/*     */         {
/* 673 */           Thaumcraft.proxy.getPlayerKnowledge().setWarpCounter(player, 0);
/*     */         }
/*     */         
/*     */       }
/*     */     }
/*     */     catch (Exception exception1)
/*     */     {
/* 680 */       exception1.printStackTrace();
/* 681 */       Thaumcraft.log.fatal("Error loading Thaumcraft data");
/*     */     }
/*     */   }
/*     */   
/*     */   public static void loadResearchNBT(NBTTagCompound entityData, String player)
/*     */   {
/* 687 */     NBTTagList tagList = entityData.getTagList("THAUMCRAFT.RESEARCH", 10);
/* 688 */     for (int j = 0; j < tagList.tagCount(); j++) {
/* 689 */       NBTTagCompound rs = tagList.getCompoundTagAt(j);
/* 690 */       if (rs.hasKey("key")) {
/* 691 */         completeResearchUnsaved(player, rs.getString("key"), rs.getByte("flags"));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static boolean savePlayerData(EntityPlayer player, File file1, File file2)
/*     */   {
/* 699 */     boolean success = true;
/*     */     try
/*     */     {
/* 702 */       NBTTagCompound data = new NBTTagCompound();
/*     */       
/* 704 */       saveResearchNBT(data, player);
/*     */       
/* 706 */       data.setTag("Thaumcraft.eldritch", new NBTTagInt(Thaumcraft.proxy.getPlayerKnowledge().getWarpPerm(player.getName())));
/*     */       
/*     */ 
/* 709 */       data.setTag("Thaumcraft.eldritch.temp", new NBTTagInt(Thaumcraft.proxy.getPlayerKnowledge().getWarpTemp(player.getName())));
/*     */       
/*     */ 
/* 712 */       data.setTag("Thaumcraft.eldritch.sticky", new NBTTagInt(Thaumcraft.proxy.getPlayerKnowledge().getWarpSticky(player.getName())));
/*     */       
/*     */ 
/* 715 */       data.setTag("Thaumcraft.eldritch.counter", new NBTTagInt(Thaumcraft.proxy.getPlayerKnowledge().getWarpCounter(player.getName())));
/*     */       
/*     */ 
/* 718 */       if ((file1 != null) && (file1.exists())) {
/*     */         try {
/* 720 */           Files.copy(file1, file2);
/*     */         }
/*     */         catch (Exception e) {
/* 723 */           Thaumcraft.log.error("Could not backup old research file for player " + player.getName());
/*     */         }
/*     */       }
/*     */       try
/*     */       {
/* 728 */         if (file1 != null)
/*     */         {
/* 730 */           FileOutputStream fileoutputstream = new FileOutputStream(file1);
/* 731 */           CompressedStreamTools.writeCompressed(data, fileoutputstream);
/* 732 */           fileoutputstream.close();
/*     */         }
/*     */       } catch (Exception e) {
/* 735 */         Thaumcraft.log.error("Could not save research file for player " + player.getName());
/* 736 */         if (file1.exists()) {
/*     */           try {
/* 738 */             file1.delete();
/*     */           }
/*     */           catch (Exception e2) {}
/*     */         }
/* 742 */         success = false;
/*     */       }
/*     */     }
/*     */     catch (Exception exception1)
/*     */     {
/* 747 */       exception1.printStackTrace();
/* 748 */       Thaumcraft.log.fatal("Error saving Thaumcraft data");
/* 749 */       success = false;
/*     */     }
/* 751 */     return success;
/*     */   }
/*     */   
/*     */   public static void saveResearchNBT(NBTTagCompound entityData, EntityPlayer player) {
/* 755 */     NBTTagList tagList = new NBTTagList();
/*     */     
/* 757 */     ArrayList<String> res = getResearchForPlayer(player.getName());
/* 758 */     HashMap<String, Byte> flags = getResearchFlagsForPlayer(player.getName());
/* 759 */     if ((res != null) && (res.size() > 0)) {
/* 760 */       for (String key : res) {
/* 761 */         if (key != null)
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/* 766 */           NBTTagCompound f = new NBTTagCompound();
/* 767 */           f.setString("key", key);
/* 768 */           byte b = 0;
/* 769 */           if (flags.containsKey(key)) b = ((Byte)flags.get(key)).byteValue();
/* 770 */           f.setByte("flags", b);
/* 771 */           tagList.appendTag(f);
/*     */         }
/*     */       }
/*     */     }
/* 775 */     entityData.setTag("THAUMCRAFT.RESEARCH", tagList);
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\research\ResearchManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */