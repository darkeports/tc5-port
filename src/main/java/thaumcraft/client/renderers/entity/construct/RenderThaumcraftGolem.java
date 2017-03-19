/*     */ package thaumcraft.client.renderers.entity.construct;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.entity.RenderBiped;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.EventBus;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import thaumcraft.api.golems.EnumGolemTrait;
/*     */ import thaumcraft.api.golems.IGolemProperties;
/*     */ import thaumcraft.api.golems.parts.GolemAddon;
/*     */ import thaumcraft.api.golems.parts.GolemArm;
/*     */ import thaumcraft.api.golems.parts.GolemHead;
/*     */ import thaumcraft.api.golems.parts.GolemLeg;
/*     */ import thaumcraft.api.golems.parts.GolemMaterial;
/*     */ import thaumcraft.api.golems.parts.PartModel;
/*     */ import thaumcraft.api.golems.parts.PartModel.EnumAttachPoint;
/*     */ import thaumcraft.api.golems.parts.PartModel.EnumLimbSide;
/*     */ import thaumcraft.client.lib.models.AdvancedModelLoader;
/*     */ import thaumcraft.client.lib.models.IModelCustom;
/*     */ import thaumcraft.common.entities.construct.golem.EntityThaumcraftGolem;
/*     */ 
/*     */ @net.minecraftforge.fml.relauncher.SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*     */ public class RenderThaumcraftGolem extends RenderBiped
/*     */ {
/*  43 */   private static final Logger logger = ;
/*     */   
/*  45 */   private HashMap<String, IModelCustom> models = new HashMap();
/*     */   
/*  47 */   private HashMap<Integer, HashMap<PartModel.EnumAttachPoint, ArrayList<PartModel>>> partsCache = new HashMap();
/*     */   
/*     */   private IModelCustom baseModel;
/*     */   
/*     */   public RenderThaumcraftGolem(RenderManager p_i46127_1_)
/*     */   {
/*  53 */     super(p_i46127_1_, new net.minecraft.client.model.ModelBiped(), 0.3F);
/*  54 */     this.layerRenderers.clear();
/*  55 */     this.baseModel = AdvancedModelLoader.loadModel(new ResourceLocation("thaumcraft", "models/obj/golem_base.obj"));
/*     */   }
/*     */   
/*     */   private void renderModel(EntityThaumcraftGolem entity, float p1, float p2, float p3, float p4, float p5, float p6, float partialTicks)
/*     */   {
/*  60 */     boolean flag = !entity.isInvisible();
/*  61 */     boolean flag1 = (!flag) && (!entity.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer));
/*     */     
/*  63 */     if ((flag) || (flag1))
/*     */     {
/*  65 */       if (flag1)
/*     */       {
/*  67 */         GlStateManager.pushMatrix();
/*  68 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 0.15F);
/*  69 */         GlStateManager.depthMask(false);
/*  70 */         GlStateManager.enableBlend();
/*  71 */         GlStateManager.blendFunc(770, 771);
/*  72 */         GlStateManager.alphaFunc(516, 0.003921569F);
/*     */       }
/*     */       
/*  75 */       renderParts(entity, p1, p2, p3, p4, p5, p6, partialTicks);
/*     */       
/*  77 */       if (flag1)
/*     */       {
/*  79 */         GlStateManager.disableBlend();
/*  80 */         GlStateManager.alphaFunc(516, 0.1F);
/*  81 */         GlStateManager.popMatrix();
/*  82 */         GlStateManager.depthMask(true);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void renderParts(EntityThaumcraftGolem entity, float limbSwing, float prevLimbSwing, float rotFloat, float headPitch, float headYaw, float p_78087_6_, float partialTicks) {
/*  88 */     ResourceLocation matTexture = entity.getProperties().getMaterial().texture;
/*     */     
/*  90 */     boolean holding = entity.getHeldItem() != null;
/*  91 */     boolean aflag = (entity.getProperties().hasTrait(EnumGolemTrait.WHEELED)) || (entity.getProperties().hasTrait(EnumGolemTrait.FLYER));
/*  92 */     Vec3 v1 = new Vec3(entity.posX, 0.0D, entity.posZ);
/*  93 */     Vec3 v2 = new Vec3(entity.prevPosX, 0.0D, entity.prevPosZ);
/*  94 */     double speed = v1.squareDistanceTo(v2);
/*     */     
/*  96 */     if (!this.partsCache.containsKey(Integer.valueOf(entity.getEntityId()))) { createPartsCache(entity);
/*     */     }
/*  98 */     float f1 = 0.0F;
/*  99 */     float bry = 0.0F;
/* 100 */     float rx = (float)Math.toDegrees(MathHelper.sin(rotFloat * 0.067F) * 0.03F);
/* 101 */     float rz = (float)Math.toDegrees(MathHelper.cos(rotFloat * 0.09F) * 0.05F + 0.05F);
/* 102 */     float rrx = 0.0F;
/* 103 */     float rry = 0.0F;
/* 104 */     float rrz = 0.0F;
/* 105 */     float rlx = 0.0F;
/* 106 */     float rly = 0.0F;
/* 107 */     float rlz = 0.0F;
/*     */     
/* 109 */     if (holding) {
/* 110 */       rrx = 90.0F - rz / 2.0F;
/* 111 */       rrz = -2.0F;
/* 112 */       rlx = 90.0F - rz / 2.0F;
/* 113 */       rlz = 2.0F;
/*     */     } else {
/* 115 */       if (aflag) {
/* 116 */         rrx = rx * 2.0F;
/* 117 */         rlx = -rx * 2.0F;
/*     */       } else {
/* 119 */         f1 = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 2.0F * prevLimbSwing * 0.5F;
/* 120 */         rrx = (float)(Math.toDegrees(f1) + rx);
/* 121 */         f1 = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * prevLimbSwing * 0.5F;
/* 122 */         rlx = (float)(Math.toDegrees(f1) - rx);
/*     */       }
/* 124 */       rrz += rz + 2.0F;
/* 125 */       rlz -= rz + 2.0F;
/*     */     }
/*     */     
/* 128 */     if (this.swingProgress > 0.0F)
/*     */     {
/* 130 */       float wiggle = -MathHelper.sin(MathHelper.sqrt_float(this.swingProgress) * 3.1415927F * 2.0F) * 0.2F;
/* 131 */       bry = (float)Math.toDegrees(wiggle);
/* 132 */       rrz = -(float)Math.toDegrees(MathHelper.sin(wiggle) * 3.0F);
/* 133 */       rrx = (float)Math.toDegrees(-MathHelper.cos(wiggle) * 5.0F);
/* 134 */       rry += bry;
/*     */     }
/*     */     
/* 137 */     GL11.glEnable(3042);
/* 138 */     GL11.glBlendFunc(770, 771);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 143 */     GlStateManager.rotate(bry, 0.0F, 1.0F, 0.0F);
/*     */     
/* 145 */     float lean = 25.0F;
/* 146 */     if (aflag) lean = 75.0F;
/* 147 */     GlStateManager.rotate((float)(speed * lean), -1.0F, 0.0F, 0.0F);
/* 148 */     GlStateManager.rotate((float)(speed * lean * 0.06D * (entity.rotationYaw - entity.prevRotationYaw)), 0.0F, 0.0F, -1.0F);
/*     */     
/* 150 */     GlStateManager.pushMatrix();
/* 151 */     GlStateManager.translate(0.0D, 0.5D, 0.0D);
/* 152 */     bindTexture(matTexture);
/* 153 */     this.baseModel.renderPart("chest");
/* 154 */     this.baseModel.renderPart("waist");
/* 155 */     if (entity.getGolemColor() > 0) {
/* 156 */       Color c = new Color(EnumDyeColor.byMetadata(entity.getGolemColor() - 1).getMapColor().colorValue);
/* 157 */       GL11.glColor4f(c.getRed() / 255.0F, c.getGreen() / 255.0F, c.getBlue() / 255.0F, 1.0F);
/* 158 */       this.baseModel.renderPart("flag");
/* 159 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     }
/* 161 */     for (PartModel part : (ArrayList)((HashMap)this.partsCache.get(Integer.valueOf(entity.getEntityId()))).get(PartModel.EnumAttachPoint.BODY)) {
/* 162 */       renderPart(entity, part.getObjModel().toString(), part, matTexture, partialTicks, PartModel.EnumLimbSide.MIDDLE);
/*     */     }
/* 164 */     GlStateManager.popMatrix();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 170 */     GlStateManager.pushMatrix();
/* 171 */     GlStateManager.translate(0.0D, 0.75D, -0.03125D);
/* 172 */     GlStateManager.rotate(headPitch, 0.0F, -1.0F, 0.0F);
/* 173 */     GlStateManager.rotate(headYaw, -1.0F, 0.0F, 0.0F);
/* 174 */     for (PartModel part : (ArrayList)((HashMap)this.partsCache.get(Integer.valueOf(entity.getEntityId()))).get(PartModel.EnumAttachPoint.HEAD)) {
/* 175 */       renderPart(entity, part.getObjModel().toString(), part, matTexture, partialTicks, PartModel.EnumLimbSide.MIDDLE);
/*     */     }
/* 177 */     bindTexture(matTexture);
/* 178 */     this.baseModel.renderPart("head");
/* 179 */     GlStateManager.popMatrix();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 185 */     GlStateManager.pushMatrix();
/* 186 */     GlStateManager.translate(0.20625D, 0.6875D, 0.0D);
/* 187 */     Iterator i$ = ((ArrayList)((HashMap)this.partsCache.get(Integer.valueOf(entity.getEntityId()))).get(PartModel.EnumAttachPoint.ARMS)).iterator(); if (i$.hasNext()) { PartModel part = (PartModel)i$.next();
/* 188 */       rrx = part.preRenderArmRotationX(entity, partialTicks, PartModel.EnumLimbSide.RIGHT, rrx);
/* 189 */       rry = part.preRenderArmRotationY(entity, partialTicks, PartModel.EnumLimbSide.RIGHT, rry);
/* 190 */       rrz = part.preRenderArmRotationZ(entity, partialTicks, PartModel.EnumLimbSide.RIGHT, rrz);
/*     */     }
/*     */     
/* 193 */     GlStateManager.rotate(rrx, 1.0F, 0.0F, 0.0F);
/* 194 */     GlStateManager.rotate(rry, 0.0F, 1.0F, 0.0F);
/* 195 */     GlStateManager.rotate(rrz, 0.0F, 0.0F, 1.0F);
/* 196 */     bindTexture(matTexture);
/* 197 */     this.baseModel.renderPart("arm");
/* 198 */     for (PartModel part : (ArrayList)((HashMap)this.partsCache.get(Integer.valueOf(entity.getEntityId()))).get(PartModel.EnumAttachPoint.ARMS)) {
/* 199 */       renderPart(entity, part.getObjModel().toString(), part, matTexture, partialTicks, PartModel.EnumLimbSide.RIGHT);
/*     */     }
/* 201 */     GlStateManager.popMatrix();
/*     */     
/*     */ 
/* 204 */     GlStateManager.pushMatrix();
/* 205 */     GlStateManager.translate(-0.20625D, 0.6875D, 0.0D);
/* 206 */     Iterator i$ = ((ArrayList)((HashMap)this.partsCache.get(Integer.valueOf(entity.getEntityId()))).get(PartModel.EnumAttachPoint.ARMS)).iterator(); if (i$.hasNext()) { PartModel part = (PartModel)i$.next();
/* 207 */       rlx = part.preRenderArmRotationX(entity, partialTicks, PartModel.EnumLimbSide.LEFT, rlx);
/* 208 */       rly = part.preRenderArmRotationY(entity, partialTicks, PartModel.EnumLimbSide.LEFT, rly);
/* 209 */       rlz = part.preRenderArmRotationZ(entity, partialTicks, PartModel.EnumLimbSide.LEFT, rlz);
/*     */     }
/*     */     
/* 212 */     GlStateManager.rotate(rlx, 1.0F, 0.0F, 0.0F);
/* 213 */     GlStateManager.rotate(rly + 180.0F, 0.0F, 1.0F, 0.0F);
/* 214 */     GlStateManager.rotate(rlz, 0.0F, 0.0F, -1.0F);
/* 215 */     bindTexture(matTexture);
/* 216 */     this.baseModel.renderPart("arm");
/* 217 */     for (PartModel part : (ArrayList)((HashMap)this.partsCache.get(Integer.valueOf(entity.getEntityId()))).get(PartModel.EnumAttachPoint.ARMS)) {
/* 218 */       renderPart(entity, part.getObjModel().toString(), part, matTexture, partialTicks, PartModel.EnumLimbSide.LEFT);
/*     */     }
/* 220 */     GlStateManager.popMatrix();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 226 */     GlStateManager.pushMatrix();
/* 227 */     GlStateManager.translate(0.09375D, 0.375D, 0.0D);
/*     */     
/* 229 */     f1 = MathHelper.cos(limbSwing * 0.6662F) * prevLimbSwing;
/* 230 */     GlStateManager.rotate((float)Math.toDegrees(f1), 1.0F, 0.0F, 0.0F);
/* 231 */     for (PartModel part : (ArrayList)((HashMap)this.partsCache.get(Integer.valueOf(entity.getEntityId()))).get(PartModel.EnumAttachPoint.LEGS)) {
/* 232 */       renderPart(entity, part.getObjModel().toString(), part, matTexture, partialTicks, PartModel.EnumLimbSide.RIGHT);
/*     */     }
/* 234 */     GlStateManager.popMatrix();
/*     */     
/*     */ 
/* 237 */     GlStateManager.pushMatrix();
/* 238 */     GlStateManager.translate(-0.09375D, 0.375D, 0.0D);
/* 239 */     f1 = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * prevLimbSwing;
/* 240 */     GlStateManager.rotate((float)Math.toDegrees(f1), 1.0F, 0.0F, 0.0F);
/* 241 */     for (PartModel part : (ArrayList)((HashMap)this.partsCache.get(Integer.valueOf(entity.getEntityId()))).get(PartModel.EnumAttachPoint.LEGS)) {
/* 242 */       renderPart(entity, part.getObjModel().toString(), part, matTexture, partialTicks, PartModel.EnumLimbSide.LEFT);
/*     */     }
/* 244 */     GlStateManager.popMatrix();
/*     */     
/* 246 */     GL11.glDisable(3042);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 251 */     GlStateManager.pushMatrix();
/* 252 */     GlStateManager.translate(0.0D, 0.625D, 0.0D);
/* 253 */     GlStateManager.rotate(90.0F - rz * 0.5F, 1.0F, 0.0F, 0.0F);
/* 254 */     drawHeldItem(entity);
/* 255 */     GlStateManager.popMatrix();
/*     */   }
/*     */   
/*     */ 
/*     */   private void drawHeldItem(EntityThaumcraftGolem entity)
/*     */   {
/* 261 */     ItemStack itemstack = entity.getHeldItem();
/* 262 */     if (itemstack != null)
/*     */     {
/* 264 */       GlStateManager.pushMatrix();
/* 265 */       Item item = itemstack.getItem();
/* 266 */       Minecraft minecraft = Minecraft.getMinecraft();
/*     */       
/* 268 */       GlStateManager.rotate(90.0F, -1.0F, 0.0F, 0.0F);
/* 269 */       GlStateManager.scale(0.375D, 0.375D, 0.375D);
/* 270 */       GlStateManager.translate(0.0F, 0.33F, -1.0F);
/*     */       
/* 272 */       if (!(item instanceof net.minecraft.item.ItemBlock)) {
/* 273 */         GlStateManager.translate(0.0F, 0.0F, 0.25F);
/*     */       }
/*     */       
/* 276 */       minecraft.getItemRenderer().renderItem(entity, itemstack, net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType.HEAD);
/*     */       
/* 278 */       GlStateManager.popMatrix();
/*     */     }
/*     */   }
/*     */   
/*     */   private void renderPart(EntityThaumcraftGolem golem, String partName, PartModel part, ResourceLocation matTexture, float partialTicks, PartModel.EnumLimbSide side)
/*     */   {
/* 284 */     IModelCustom model = (IModelCustom)this.models.get(partName);
/* 285 */     if (model == null) {
/* 286 */       model = AdvancedModelLoader.loadModel(part.getObjModel());
/* 287 */       if (model != null)
/* 288 */         this.models.put(partName, model); else {
/* 289 */         return;
/*     */       }
/*     */     }
/* 292 */     for (String op : model.getPartNames()) {
/* 293 */       GL11.glPushMatrix();
/*     */       
/* 295 */       if (part.useMaterialTextureForObjectPart(op)) {
/* 296 */         bindTexture(matTexture);
/*     */       } else {
/* 298 */         bindTexture(part.getTexture());
/*     */       }
/* 300 */       part.preRenderObjectPart(op, golem, partialTicks, side);
/*     */       
/* 302 */       model.renderPart(op);
/*     */       
/* 304 */       part.postRenderObjectPart(op, golem, partialTicks, side);
/*     */       
/* 306 */       GL11.glPopMatrix();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/* 311 */   float swingProgress = 0.0F;
/*     */   
/*     */ 
/*     */   private void doRender(EntityThaumcraftGolem entity, double x, double y, double z, float p_76986_8_, float partialTicks)
/*     */   {
/* 316 */     if (MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Pre(entity, this, x, y, z))) return;
/* 317 */     GlStateManager.pushMatrix();
/* 318 */     GlStateManager.disableCull();
/*     */     
/* 320 */     this.swingProgress = getSwingProgress(entity, partialTicks);
/*     */     
/*     */ 
/*     */     try
/*     */     {
/* 325 */       float f2 = interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, partialTicks);
/* 326 */       float f3 = interpolateRotation(entity.prevRotationYawHead, entity.rotationYawHead, partialTicks);
/* 327 */       float f4 = f3 - f2;
/*     */       
/*     */ 
/* 330 */       if ((entity.isRiding()) && ((entity.ridingEntity instanceof EntityLivingBase)))
/*     */       {
/* 332 */         EntityLivingBase entitylivingbase1 = (EntityLivingBase)entity.ridingEntity;
/* 333 */         f2 = interpolateRotation(entitylivingbase1.prevRenderYawOffset, entitylivingbase1.renderYawOffset, partialTicks);
/* 334 */         f4 = f3 - f2;
/* 335 */         float f5 = MathHelper.wrapAngleTo180_float(f4);
/*     */         
/* 337 */         if (f5 < -85.0F)
/*     */         {
/* 339 */           f5 = -85.0F;
/*     */         }
/*     */         
/* 342 */         if (f5 >= 85.0F)
/*     */         {
/* 344 */           f5 = 85.0F;
/*     */         }
/*     */         
/* 347 */         f2 = f3 - f5;
/*     */         
/* 349 */         if (f5 * f5 > 2500.0F)
/*     */         {
/* 351 */           f2 += f5 * 0.2F;
/*     */         }
/*     */       }
/*     */       
/* 355 */       float f9 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
/* 356 */       renderLivingAt(entity, x, y, z);
/* 357 */       float f5 = handleRotationFloat(entity, partialTicks);
/* 358 */       rotateCorpse(entity, f5, f2, partialTicks);
/* 359 */       GlStateManager.enableRescaleNormal();
/* 360 */       preRenderCallback(entity, partialTicks);
/* 361 */       float f6 = 0.0625F;
/* 362 */       float f7 = entity.prevLimbSwingAmount + (entity.limbSwingAmount - entity.prevLimbSwingAmount) * partialTicks;
/* 363 */       float f8 = entity.limbSwing - entity.limbSwingAmount * (1.0F - partialTicks);
/*     */       
/* 365 */       if (f7 > 1.0F)
/*     */       {
/* 367 */         f7 = 1.0F;
/*     */       }
/*     */       
/* 370 */       GlStateManager.enableAlpha();
/*     */       
/*     */ 
/*     */ 
/* 374 */       if (this.renderOutlines)
/*     */       {
/* 376 */         boolean flag = setScoreTeamColor(entity);
/* 377 */         renderModel(entity, f8, f7, f5, f4, f9, 0.0625F, partialTicks);
/*     */         
/* 379 */         if (flag)
/*     */         {
/* 381 */           unsetScoreTeamColor();
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 386 */         boolean flag = setDoRenderBrightness(entity, partialTicks);
/* 387 */         renderModel(entity, f8, f7, f5, f4, f9, 0.0625F, partialTicks);
/*     */         
/* 389 */         if (flag)
/*     */         {
/* 391 */           unsetBrightness();
/*     */         }
/*     */         
/* 394 */         GlStateManager.depthMask(true);
/*     */         
/* 396 */         renderLayers(entity, f8, f7, partialTicks, f5, f4, f9, 0.0625F);
/*     */       }
/*     */       
/* 399 */       GlStateManager.disableRescaleNormal();
/*     */ 
/*     */     }
/*     */     catch (Exception exception)
/*     */     {
/* 404 */       logger.error("Couldn't render entity", exception);
/*     */     }
/*     */     
/* 407 */     GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 408 */     GlStateManager.enableTexture2D();
/* 409 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/* 410 */     GlStateManager.enableCull();
/* 411 */     GlStateManager.popMatrix();
/*     */     
/* 413 */     if (!this.renderOutlines)
/*     */     {
/* 415 */       renderName(entity, x, y, z);
/*     */     }
/* 417 */     MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Post(entity, this, x, y, z));
/*     */     
/* 419 */     renderLeash(entity, x, y, z, p_76986_8_, partialTicks);
/*     */   }
/*     */   
/*     */   private void createPartsCache(EntityThaumcraftGolem golem) {
/* 423 */     HashMap<PartModel.EnumAttachPoint, ArrayList<PartModel>> pl = new HashMap();
/* 424 */     pl.put(PartModel.EnumAttachPoint.BODY, new ArrayList());
/* 425 */     pl.put(PartModel.EnumAttachPoint.HEAD, new ArrayList());
/* 426 */     pl.put(PartModel.EnumAttachPoint.ARMS, new ArrayList());
/* 427 */     pl.put(PartModel.EnumAttachPoint.LEGS, new ArrayList());
/*     */     
/* 429 */     IGolemProperties props = golem.getProperties();
/*     */     
/* 431 */     if (props.getHead().model != null) {
/* 432 */       ((ArrayList)pl.get(props.getHead().model.getAttachPoint())).add(props.getHead().model);
/*     */     }
/*     */     
/* 435 */     if (props.getArms().model != null) {
/* 436 */       ((ArrayList)pl.get(props.getArms().model.getAttachPoint())).add(props.getArms().model);
/*     */     }
/*     */     
/* 439 */     if (props.getLegs().model != null) {
/* 440 */       ((ArrayList)pl.get(props.getLegs().model.getAttachPoint())).add(props.getLegs().model);
/*     */     }
/*     */     
/* 443 */     if (props.getAddon().model != null) {
/* 444 */       ((ArrayList)pl.get(props.getAddon().model.getAttachPoint())).add(props.getAddon().model);
/*     */     }
/*     */     
/* 447 */     this.partsCache.put(Integer.valueOf(golem.getEntityId()), pl);
/*     */   }
/*     */   
/*     */ 
/*     */   public void doRender(EntityLiving entity, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
/*     */   {
/* 453 */     doRender((EntityThaumcraftGolem)entity, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
/*     */   }
/*     */   
/*     */ 
/*     */   protected ResourceLocation getEntityTexture(EntityLiving p_110775_1_)
/*     */   {
/* 459 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\entity\construct\RenderThaumcraftGolem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */