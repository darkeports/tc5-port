/*     */ package thaumcraft.common.items.wands;
/*     */ 
/*     */ import baubles.api.BaublesApi;
/*     */ import java.util.HashMap;
/*     */ import java.util.TreeMap;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aspects.AspectList;
/*     */ import thaumcraft.api.items.IArchitect;
/*     */ import thaumcraft.api.items.IVisDiscountGear;
/*     */ import thaumcraft.api.potions.PotionVisExhaust;
/*     */ import thaumcraft.api.research.ResearchHelper;
/*     */ import thaumcraft.api.wands.IWand;
/*     */ import thaumcraft.api.wands.ItemFocusBasic;
/*     */ import thaumcraft.common.lib.potions.PotionInfectiousVisExhaust;
/*     */ 
/*     */ public class WandManager
/*     */ {
/*  26 */   public WandTriggers triggers = new WandTriggers();
/*     */   
/*     */   public static int getBaseChargeRate(EntityPlayer player, boolean currentItem, int slot) {
/*  29 */     return currentItem ? 1 : (ResearchHelper.isResearchComplete(player.getName(), "NODETAPPER1")) && (slot < 9) ? 2 : (ResearchHelper.isResearchComplete(player.getName(), "NODETAPPER2")) && (slot < 9) ? 3 : 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public static float getTotalVisDiscount(EntityPlayer player, Aspect aspect)
/*     */   {
/*  35 */     int total = 0;
/*  36 */     if (player == null) { return 0.0F;
/*     */     }
/*  38 */     IInventory baubles = BaublesApi.getBaubles(player);
/*  39 */     for (int a = 0; a < 4; a++) {
/*  40 */       if ((baubles.getStackInSlot(a) != null) && ((baubles.getStackInSlot(a).getItem() instanceof IVisDiscountGear)))
/*     */       {
/*  42 */         total += ((IVisDiscountGear)baubles.getStackInSlot(a).getItem()).getVisDiscount(baubles.getStackInSlot(a), player, aspect);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*  47 */     for (int a = 0; a < 4; a++) {
/*  48 */       if ((player.inventory.armorItemInSlot(a) != null) && ((player.inventory.armorItemInSlot(a).getItem() instanceof IVisDiscountGear))) {
/*  49 */         total += ((IVisDiscountGear)player.inventory.armorItemInSlot(a).getItem()).getVisDiscount(player.inventory.armorItemInSlot(a), player, aspect);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*  54 */     if ((player.isPotionActive(PotionVisExhaust.instance)) || (player.isPotionActive(PotionInfectiousVisExhaust.instance)))
/*     */     {
/*  56 */       int level1 = 0;
/*  57 */       int level2 = 0;
/*  58 */       if (player.isPotionActive(PotionVisExhaust.instance)) {
/*  59 */         level1 = player.getActivePotionEffect(PotionVisExhaust.instance).getAmplifier();
/*     */       }
/*  61 */       if (player.isPotionActive(PotionInfectiousVisExhaust.instance)) {
/*  62 */         level2 = player.getActivePotionEffect(PotionInfectiousVisExhaust.instance).getAmplifier();
/*     */       }
/*  64 */       total -= (Math.max(level1, level2) + 1) * 10;
/*     */     }
/*     */     
/*  67 */     return total / 100.0F;
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
/*     */   public static boolean consumeVisFromInventory(EntityPlayer player, AspectList cost)
/*     */   {
/*  81 */     for (int a = player.inventory.mainInventory.length - 1; a >= 0; a--) {
/*  82 */       ItemStack item = player.inventory.mainInventory[a];
/*  83 */       if ((item != null) && ((item.getItem() instanceof IWand))) {
/*  84 */         boolean done = ((IWand)item.getItem()).consumeAllVis(item, player, cost, true, true);
/*  85 */         if (done) return true;
/*     */       }
/*     */     }
/*  88 */     return false;
/*     */   }
/*     */   
/*     */   public static void changeFocus(ItemStack is, World w, EntityPlayer player, String focus) {
/*  92 */     IWand wand = (IWand)is.getItem();
/*  93 */     TreeMap<String, Integer> foci = new TreeMap();
/*  94 */     HashMap<Integer, Integer> pouches = new HashMap();
/*  95 */     int pouchcount = 0;
/*  96 */     ItemStack item = null;
/*     */     
/*     */ 
/*  99 */     IInventory baubles = BaublesApi.getBaubles(player);
/* 100 */     for (int a = 0; a < 4; a++) {
/* 101 */       if ((baubles.getStackInSlot(a) != null) && ((baubles.getStackInSlot(a).getItem() instanceof ItemFocusPouch)))
/*     */       {
/* 103 */         pouchcount++;
/* 104 */         item = baubles.getStackInSlot(a);
/* 105 */         pouches.put(Integer.valueOf(pouchcount), Integer.valueOf(a - 4));
/* 106 */         ItemStack[] inv = ((ItemFocusPouch)item.getItem()).getInventory(item);
/* 107 */         for (int q = 0; q < inv.length; q++) {
/* 108 */           item = inv[q];
/* 109 */           if ((item != null) && ((item.getItem() instanceof ItemFocusBasic))) {
/* 110 */             foci.put(((ItemFocusBasic)item.getItem()).getSortingHelper(item), Integer.valueOf(q + pouchcount * 1000));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 117 */     for (int a = 0; a < 36; a++) {
/* 118 */       item = player.inventory.mainInventory[a];
/* 119 */       if ((item != null) && ((item.getItem() instanceof ItemFocusBasic))) {
/* 120 */         foci.put(((ItemFocusBasic)item.getItem()).getSortingHelper(item), Integer.valueOf(a));
/*     */       }
/* 122 */       if ((item != null) && ((item.getItem() instanceof ItemFocusPouch))) {
/* 123 */         pouchcount++;
/* 124 */         pouches.put(Integer.valueOf(pouchcount), Integer.valueOf(a));
/* 125 */         ItemStack[] inv = ((ItemFocusPouch)item.getItem()).getInventory(item);
/* 126 */         for (int q = 0; q < inv.length; q++) {
/* 127 */           item = inv[q];
/* 128 */           if ((item != null) && ((item.getItem() instanceof ItemFocusBasic))) {
/* 129 */             foci.put(((ItemFocusBasic)item.getItem()).getSortingHelper(item), Integer.valueOf(q + pouchcount * 1000));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 135 */     if ((focus.equals("REMOVE")) || (foci.size() == 0)) {
/* 136 */       if ((wand.getFocus(is) != null) && (
/* 137 */         (addFocusToPouch(player, wand.getFocusStack(is).copy(), pouches)) || (player.inventory.addItemStackToInventory(wand.getFocusStack(is).copy()))))
/*     */       {
/* 139 */         wand.setFocus(is, null);
/* 140 */         w.playSoundAtEntity(player, "thaumcraft:cameraticks", 0.3F, 0.9F);
/*     */       }
/*     */       
/* 143 */       return; }
/* 144 */     if ((foci != null) && (foci.size() > 0) && (focus != null))
/*     */     {
/*     */ 
/* 147 */       String newkey = focus;
/* 148 */       if (foci.get(newkey) == null) newkey = (String)foci.higherKey(newkey);
/* 149 */       if ((newkey == null) || (foci.get(newkey) == null)) { newkey = (String)foci.firstKey();
/*     */       }
/* 151 */       if ((((Integer)foci.get(newkey)).intValue() < 1000) && (((Integer)foci.get(newkey)).intValue() >= 0)) {
/* 152 */         item = player.inventory.mainInventory[((Integer)foci.get(newkey)).intValue()].copy();
/*     */       } else {
/* 154 */         int pid = ((Integer)foci.get(newkey)).intValue() / 1000;
/* 155 */         if (pouches.containsKey(Integer.valueOf(pid))) {
/* 156 */           int pouchslot = ((Integer)pouches.get(Integer.valueOf(pid))).intValue();
/* 157 */           int focusslot = ((Integer)foci.get(newkey)).intValue() - pid * 1000;
/* 158 */           ItemStack tmp = null;
/* 159 */           if (pouchslot >= 0) {
/* 160 */             tmp = player.inventory.mainInventory[pouchslot].copy();
/*     */           } else {
/* 162 */             tmp = baubles.getStackInSlot(pouchslot + 4).copy();
/*     */           }
/* 164 */           item = fetchFocusFromPouch(player, focusslot, tmp, pouchslot);
/*     */         }
/*     */       }
/*     */       
/* 168 */       if (item != null) {
/* 169 */         if ((((Integer)foci.get(newkey)).intValue() < 1000) && (((Integer)foci.get(newkey)).intValue() >= 0)) {
/* 170 */           player.inventory.setInventorySlotContents(((Integer)foci.get(newkey)).intValue(), null);
/*     */         }
/*     */         
/*     */       }
/*     */       else {
/* 175 */         return;
/*     */       }
/*     */       
/* 178 */       w.playSoundAtEntity(player, "thaumcraft:cameraticks", 0.3F, 1.0F);
/*     */       
/*     */ 
/* 181 */       if ((wand.getFocus(is) != null) && (
/* 182 */         (addFocusToPouch(player, wand.getFocusStack(is).copy(), pouches)) || (player.inventory.addItemStackToInventory(wand.getFocusStack(is).copy()))))
/*     */       {
/* 184 */         wand.setFocus(is, null);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 189 */       if (wand.getFocus(is) == null) {
/* 190 */         wand.setFocus(is, item);
/*     */       }
/* 192 */       else if (!addFocusToPouch(player, item, pouches)) {
/* 193 */         player.inventory.addItemStackToInventory(item);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static ItemStack fetchFocusFromPouch(EntityPlayer player, int focusid, ItemStack pouch, int pouchslot)
/*     */   {
/* 200 */     ItemStack focus = null;
/* 201 */     ItemStack[] inv = ((ItemFocusPouch)pouch.getItem()).getInventory(pouch);
/* 202 */     ItemStack contents = inv[focusid];
/* 203 */     if ((contents != null) && ((contents.getItem() instanceof ItemFocusBasic))) {
/* 204 */       focus = contents.copy();
/* 205 */       inv[focusid] = null;
/* 206 */       ((ItemFocusPouch)pouch.getItem()).setInventory(pouch, inv);
/* 207 */       if (pouchslot >= 0) {
/* 208 */         player.inventory.setInventorySlotContents(pouchslot, pouch);
/* 209 */         player.inventory.markDirty();
/*     */       } else {
/* 211 */         IInventory baubles = BaublesApi.getBaubles(player);
/* 212 */         baubles.setInventorySlotContents(pouchslot + 4, pouch);
/* 213 */         baubles.markDirty();
/*     */       }
/*     */     }
/* 216 */     return focus;
/*     */   }
/*     */   
/*     */   private static boolean addFocusToPouch(EntityPlayer player, ItemStack focus, HashMap<Integer, Integer> pouches)
/*     */   {
/* 221 */     IInventory baubles = BaublesApi.getBaubles(player);
/* 222 */     for (Integer pouchslot : pouches.values()) { ItemStack pouch;
/* 223 */       ItemStack pouch; if (pouchslot.intValue() >= 0) {
/* 224 */         pouch = player.inventory.mainInventory[pouchslot.intValue()];
/*     */       } else {
/* 226 */         pouch = baubles.getStackInSlot(pouchslot.intValue() + 4);
/*     */       }
/* 228 */       ItemStack[] inv = ((ItemFocusPouch)pouch.getItem()).getInventory(pouch);
/*     */       
/* 230 */       for (int q = 0; q < inv.length; q++) {
/* 231 */         ItemStack contents = inv[q];
/* 232 */         if (contents == null) {
/* 233 */           inv[q] = focus.copy();
/* 234 */           ((ItemFocusPouch)pouch.getItem()).setInventory(pouch, inv);
/* 235 */           if (pouchslot.intValue() >= 0) {
/* 236 */             player.inventory.setInventorySlotContents(pouchslot.intValue(), pouch);
/* 237 */             player.inventory.markDirty();
/*     */           } else {
/* 239 */             baubles.setInventorySlotContents(pouchslot.intValue() + 4, pouch);
/* 240 */             baubles.markDirty();
/*     */           }
/* 242 */           return true;
/*     */         }
/*     */       }
/*     */     }
/* 246 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public static void toggleMisc(ItemStack itemstack, World world, EntityPlayer player)
/*     */   {
/* 252 */     if (!(itemstack.getItem() instanceof IWand)) return;
/* 253 */     IWand wand = (IWand)itemstack.getItem();
/* 254 */     if ((wand.getFocus(itemstack) != null) && ((wand.getFocus(itemstack) instanceof IArchitect)) && (wand.getFocus(itemstack).isUpgradedWith(wand.getFocusStack(itemstack), thaumcraft.api.wands.FocusUpgradeType.architect)))
/*     */     {
/* 256 */       int dim = getAreaDim(itemstack);
/* 257 */       IArchitect fa = (IArchitect)wand.getFocus(itemstack);
/* 258 */       if (player.isSneaking()) {
/* 259 */         dim++;
/* 260 */         if (dim > ((wand.getFocusStack(itemstack).getItem() instanceof thaumcraft.common.items.wands.foci.ItemFocusTrade) ? 2 : 3)) dim = 0;
/* 261 */         setAreaDim(itemstack, dim);
/*     */       } else {
/* 263 */         int areax = getAreaX(itemstack);
/* 264 */         int areay = getAreaY(itemstack);
/* 265 */         int areaz = getAreaZ(itemstack);
/* 266 */         if (dim == 0) {
/* 267 */           areax++;
/* 268 */           areaz++;
/* 269 */           areay++;
/* 270 */         } else if (dim == 1) {
/* 271 */           areax++;
/* 272 */         } else if (dim == 2) {
/* 273 */           areaz++;
/* 274 */         } else if (dim == 3) {
/* 275 */           areay++;
/*     */         }
/* 277 */         if (areax > wand.getFocus(itemstack).getMaxAreaSize(wand.getFocusStack(itemstack))) {
/* 278 */           areax = 0;
/*     */         }
/* 280 */         if (areaz > wand.getFocus(itemstack).getMaxAreaSize(wand.getFocusStack(itemstack))) {
/* 281 */           areaz = 0;
/*     */         }
/* 283 */         if (areay > wand.getFocus(itemstack).getMaxAreaSize(wand.getFocusStack(itemstack))) {
/* 284 */           areay = 0;
/*     */         }
/* 286 */         setAreaX(itemstack, areax);
/* 287 */         setAreaY(itemstack, areay);
/* 288 */         setAreaZ(itemstack, areaz);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static int getAreaDim(ItemStack stack)
/*     */   {
/* 295 */     if ((stack.hasTagCompound()) && (stack.getTagCompound().hasKey("aread"))) {
/* 296 */       return stack.getTagCompound().getInteger("aread");
/*     */     }
/* 298 */     return 0;
/*     */   }
/*     */   
/*     */   public static int getAreaX(ItemStack stack) {
/* 302 */     IWand wand = (IWand)stack.getItem();
/* 303 */     if ((stack.hasTagCompound()) && (stack.getTagCompound().hasKey("areax"))) {
/* 304 */       int a = stack.getTagCompound().getInteger("areax");
/* 305 */       if (a > wand.getFocus(stack).getMaxAreaSize(wand.getFocusStack(stack))) {
/* 306 */         a = wand.getFocus(stack).getMaxAreaSize(wand.getFocusStack(stack));
/*     */       }
/* 308 */       return a;
/*     */     }
/* 310 */     return wand.getFocus(stack).getMaxAreaSize(wand.getFocusStack(stack));
/*     */   }
/*     */   
/*     */   public static int getAreaY(ItemStack stack) {
/* 314 */     IWand wand = (IWand)stack.getItem();
/* 315 */     if ((stack.hasTagCompound()) && (stack.getTagCompound().hasKey("areay"))) {
/* 316 */       int a = stack.getTagCompound().getInteger("areay");
/* 317 */       if (a > wand.getFocus(stack).getMaxAreaSize(wand.getFocusStack(stack))) {
/* 318 */         a = wand.getFocus(stack).getMaxAreaSize(wand.getFocusStack(stack));
/*     */       }
/* 320 */       return a;
/*     */     }
/* 322 */     return wand.getFocus(stack).getMaxAreaSize(wand.getFocusStack(stack));
/*     */   }
/*     */   
/*     */   public static int getAreaZ(ItemStack stack) {
/* 326 */     IWand wand = (IWand)stack.getItem();
/* 327 */     if ((stack.hasTagCompound()) && (stack.getTagCompound().hasKey("areaz"))) {
/* 328 */       int a = stack.getTagCompound().getInteger("areaz");
/* 329 */       if (a > wand.getFocus(stack).getMaxAreaSize(wand.getFocusStack(stack))) {
/* 330 */         a = wand.getFocus(stack).getMaxAreaSize(wand.getFocusStack(stack));
/*     */       }
/* 332 */       return a;
/*     */     }
/* 334 */     return wand.getFocus(stack).getMaxAreaSize(wand.getFocusStack(stack));
/*     */   }
/*     */   
/*     */   public static void setAreaX(ItemStack stack, int area) {
/* 338 */     if (stack.hasTagCompound()) {
/* 339 */       stack.getTagCompound().setInteger("areax", area);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void setAreaY(ItemStack stack, int area) {
/* 344 */     if (stack.hasTagCompound()) {
/* 345 */       stack.getTagCompound().setInteger("areay", area);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void setAreaZ(ItemStack stack, int area) {
/* 350 */     if (stack.hasTagCompound()) {
/* 351 */       stack.getTagCompound().setInteger("areaz", area);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void setAreaDim(ItemStack stack, int dim) {
/* 356 */     if (stack.hasTagCompound()) {
/* 357 */       stack.getTagCompound().setInteger("aread", dim);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/* 362 */   static HashMap<Integer, Long> cooldownServer = new HashMap();
/* 363 */   static HashMap<Integer, Long> cooldownClient = new HashMap();
/*     */   
/*     */   static boolean isOnCooldown(EntityLivingBase entityLiving) {
/* 366 */     if ((entityLiving.worldObj.isRemote) && (cooldownClient.containsKey(Integer.valueOf(entityLiving.getEntityId())))) {
/* 367 */       return ((Long)cooldownClient.get(Integer.valueOf(entityLiving.getEntityId()))).longValue() > System.currentTimeMillis();
/*     */     }
/* 369 */     if ((!entityLiving.worldObj.isRemote) && (cooldownServer.containsKey(Integer.valueOf(entityLiving.getEntityId())))) {
/* 370 */       return ((Long)cooldownServer.get(Integer.valueOf(entityLiving.getEntityId()))).longValue() > System.currentTimeMillis();
/*     */     }
/* 372 */     return false;
/*     */   }
/*     */   
/*     */   public static float getCooldown(EntityLivingBase entityLiving) {
/* 376 */     if ((entityLiving.worldObj.isRemote) && (cooldownClient.containsKey(Integer.valueOf(entityLiving.getEntityId())))) {
/* 377 */       return (float)(((Long)cooldownClient.get(Integer.valueOf(entityLiving.getEntityId()))).longValue() - System.currentTimeMillis()) / 1000.0F;
/*     */     }
/* 379 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public static void setCooldown(EntityLivingBase entityLiving, int cd) {
/* 383 */     if (cd == 0) {
/* 384 */       cooldownClient.remove(Integer.valueOf(entityLiving.getEntityId()));
/* 385 */       cooldownServer.remove(Integer.valueOf(entityLiving.getEntityId()));
/*     */     }
/* 387 */     else if (entityLiving.worldObj.isRemote) {
/* 388 */       cooldownClient.put(Integer.valueOf(entityLiving.getEntityId()), Long.valueOf(System.currentTimeMillis() + cd));
/*     */     } else {
/* 390 */       cooldownServer.put(Integer.valueOf(entityLiving.getEntityId()), Long.valueOf(System.currentTimeMillis() + cd));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\wands\WandManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */