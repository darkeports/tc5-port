/*     */ package thaumcraft.client.lib;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.renderer.entity.RendererLivingEntity;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.client.event.RenderLivingEvent.Post;
/*     */ import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import thaumcraft.client.lib.math.Box;
/*     */ import thaumcraft.client.lib.math.Intersection;
/*     */ import thaumcraft.client.lib.math.Line;
/*     */ import thaumcraft.client.lib.math.Vec4;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ 
/*     */ public class ClothHandler
/*     */ {
/*     */   private class ClothNode
/*     */   {
/*     */     Vec4 pos;
/*     */     Vec4 vel;
/*     */     Vec4 acc;
/*     */     double mass;
/*  41 */     boolean fixed = false;
/*  42 */     double g = -0.01D;
/*     */     
/*     */     private ClothNode(double x, double y, double z, double d, double g, boolean fixed) {
/*  45 */       this.pos = new Vec4(x, y, z);
/*  46 */       this.vel = new Vec4(0.0D, 0.0D, 0.0D);
/*  47 */       this.acc = new Vec4(0.0D, 0.0D, 0.0D);
/*  48 */       this.mass = d;
/*  49 */       this.fixed = fixed;
/*  50 */       this.g = g;
/*     */     }
/*     */     
/*     */ 
/*  54 */     private void applyForce(Vec4 force) { this.acc = this.acc.add3(force.multiply3(this.mass)); }
/*     */   }
/*     */   
/*     */   private class NodeLink {
/*     */     int a;
/*     */     int b;
/*     */     double length;
/*     */     
/*     */     private NodeLink(int a, int b, double length) {
/*  63 */       this.a = a;
/*  64 */       this.b = b;
/*  65 */       this.length = length;
/*     */     }
/*     */   }
/*     */   
/*     */   private static abstract interface IAttachPoint {
/*     */     public abstract Vec3 getFinalOffset(ClothHandler.ClothMesh paramClothMesh);
/*     */   }
/*     */   
/*     */   private class AttachPointBlock implements ClothHandler.IAttachPoint {
/*     */     Vec3 offset;
/*     */     
/*     */     private AttachPointBlock(Vec3 offset) {
/*  77 */       this.offset = offset;
/*     */     }
/*     */     
/*     */     public Vec3 getFinalOffset(ClothHandler.ClothMesh mesh)
/*     */     {
/*  82 */       return this.offset;
/*     */     }
/*     */   }
/*     */   
/*     */   private class AttachPointEntity implements ClothHandler.IAttachPoint {
/*     */     Vec3 offset;
/*     */     EntityLiving entity;
/*     */     
/*     */     private AttachPointEntity(Vec3 offset, EntityLiving entity) {
/*  91 */       this.offset = offset;
/*  92 */       this.entity = entity;
/*     */     }
/*     */     
/*     */     public Vec3 getFinalOffset(ClothHandler.ClothMesh mesh)
/*     */     {
/*  97 */       double xx = MathHelper.cos(this.entity.renderYawOffset / 180.0F * 3.1415927F) * this.offset.xCoord;
/*  98 */       double zz = MathHelper.sin(this.entity.renderYawOffset / 180.0F * 3.1415927F) * this.offset.xCoord;
/*  99 */       xx -= MathHelper.cos((this.entity.renderYawOffset + 90.0F) / 180.0F * 3.1415927F) * this.offset.zCoord;
/* 100 */       zz -= MathHelper.sin((this.entity.renderYawOffset + 90.0F) / 180.0F * 3.1415927F) * this.offset.zCoord;
/* 101 */       return new Vec3(xx, this.offset.yCoord, zz);
/*     */     } }
/*     */   
/*     */   private class ClothMesh { private ClothMesh() {}
/*     */     
/* 106 */     boolean blockPhysics = false;
/* 107 */     ArrayList<Integer> entityPhysics = new ArrayList();
/*     */     ClothMesh mesh;
/*     */     private ClothHandler.IAttachPoint attachPoint;
/*     */     private int sizeX;
/* 111 */     private int sizeY; private ArrayList<ClothHandler.ClothNode> nodes = new ArrayList();
/* 112 */     private ArrayList<ClothHandler.NodeLink> links = new ArrayList();
/*     */     
/* 114 */     Vec4[] lastCorners = null;
/*     */     
/*     */      }
/* 117 */   HashMap<Integer, ClothMesh> clothMeshes = new HashMap();
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   @SubscribeEvent
/*     */   public void livingTick(LivingEvent.LivingUpdateEvent event) {
/* 122 */     if (!event.entity.worldObj.isRemote) {
/* 123 */       return;
/*     */     }
/* 125 */     if (((event.entity instanceof EntityVillager)) && 
/* 126 */       (this.lastRender.containsKey(Integer.valueOf(event.entity.getEntityId()))) && (((Long)this.lastRender.get(Integer.valueOf(event.entity.getEntityId()))).longValue() + 80L < System.currentTimeMillis()))
/*     */     {
/* 128 */       processCapeTick(event.entityLiving, 1.0F);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void processCapeTick(EntityLivingBase entity, float timestep)
/*     */   {
/* 136 */     ClothMesh mesh = (ClothMesh)this.clothMeshes.get(Integer.valueOf(entity.getEntityId()));
/* 137 */     if (mesh == null) {
/* 138 */       mesh = createInitialState(entity);
/* 139 */       this.clothMeshes.put(Integer.valueOf(entity.getEntityId()), mesh);
/*     */     }
/*     */     
/* 142 */     calculateForces(entity, mesh, timestep);
/*     */   }
/*     */   
/*     */   public void addLink(ClothMesh mesh, NodeLink link) {
/* 146 */     mesh.links.add(link);
/*     */   }
/*     */   
/*     */ 
/*     */   private ClothMesh createInitialState(EntityLivingBase entity)
/*     */   {
/* 152 */     ClothMesh mesh = new ClothMesh(null);
/* 153 */     float ew = entity.width / 4.0F;
/* 154 */     for (int i = 0; i < 4; i++)
/*     */     {
/* 156 */       float offset = -(ew * 1.5F) + ew * i;
/* 157 */       double xx = MathHelper.cos(entity.renderYawOffset / 180.0F * 3.1415927F) * offset;
/* 158 */       double zz = MathHelper.sin(entity.renderYawOffset / 180.0F * 3.1415927F) * offset;
/* 159 */       xx -= MathHelper.cos((entity.renderYawOffset + 90.0F) / 180.0F * 3.1415927F) * ew * 1.4F;
/* 160 */       zz -= MathHelper.sin((entity.renderYawOffset + 90.0F) / 180.0F * 3.1415927F) * ew * 1.4F;
/* 161 */       mesh.nodes.add(new ClothNode(xx + entity.posX, entity.posY + entity.height * 0.75D, zz + entity.posZ, 0.02D, 0.0D, true, null));
/* 162 */       addLink(mesh, new NodeLink(i + 4, i, 0.1D, null));
/*     */     }
/* 164 */     int ind = 4;
/* 165 */     for (int i = 1; i < 5; i++)
/* 166 */       for (int q = 0; q < 4; q++)
/*     */       {
/* 168 */         ClothNode nd = (ClothNode)mesh.nodes.get(q);
/* 169 */         mesh.nodes.add(new ClothNode(nd.pos.x, nd.pos.y - i * 0.2D, nd.pos.z, 0.02D, -0.02D + i * 0.001F, false, null));
/* 170 */         if (q < 3) {
/* 171 */           addLink(mesh, new NodeLink(ind + 1, ind, 0.05D, null));
/*     */         }
/* 173 */         if (i < 4) {
/* 174 */           addLink(mesh, new NodeLink(ind + 4, ind, 0.04D, null));
/*     */         }
/* 176 */         ind++;
/*     */       }
/* 178 */     return mesh;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/* 183 */   Box body = null;
/*     */   
/*     */   private void calculateForces(EntityLivingBase entity, ClothMesh mesh, float timestep)
/*     */   {
/* 187 */     boolean first = true;
/* 188 */     float ew = entity.width / 4.0F;
/*     */     
/* 190 */     double ex = interpolateValue(entity.prevPosX, entity.posX, UtilsFX.sysPartialTicks);
/* 191 */     double ey = interpolateValue(entity.prevPosY, entity.posY, UtilsFX.sysPartialTicks) + entity.height * 0.75D;
/* 192 */     double eb = interpolateValue(entity.prevPosY, entity.posY, UtilsFX.sysPartialTicks);
/* 193 */     double ez = interpolateValue(entity.prevPosZ, entity.posZ, UtilsFX.sysPartialTicks);
/* 194 */     double fx = MathHelper.cos((entity.renderYawOffset + 90.0F) / 180.0F * 3.1415927F) * ew * 1.4F;
/* 195 */     double fz = MathHelper.sin((entity.renderYawOffset + 90.0F) / 180.0F * 3.1415927F) * ew * 1.4F;
/* 196 */     double fx2 = MathHelper.cos((entity.renderYawOffset - 90.0F) / 180.0F * 3.1415927F) * ew * 1.4F;
/* 197 */     double fz2 = MathHelper.sin((entity.renderYawOffset - 90.0F) / 180.0F * 3.1415927F) * ew * 1.4F;
/* 198 */     ArrayList<Vec4> points = new ArrayList();
/*     */     
/* 200 */     float repulsion = 0.1F;
/* 201 */     float stiffness = 9.0F;
/* 202 */     float damping = 0.85F;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 210 */     for (int i = 0; i < mesh.nodes.size(); i++)
/*     */     {
/* 212 */       ClothNode v = (ClothNode)mesh.nodes.get(i);
/*     */       
/*     */ 
/* 215 */       if ((i < 4) && (v.fixed)) {
/* 216 */         float offset = -(ew * 1.5F) + ew * i;
/* 217 */         double xx = MathHelper.cos(entity.renderYawOffset / 180.0F * 3.1415927F) * offset;
/* 218 */         double zz = MathHelper.sin(entity.renderYawOffset / 180.0F * 3.1415927F) * offset;
/* 219 */         xx -= fx;
/* 220 */         zz -= fz;
/* 221 */         v.pos = new Vec4(xx + ex, ey, zz + ez);
/*     */         
/* 223 */         if ((i == 0) || (i == 3)) {
/* 224 */           points.add(new Vec4(v.pos.x, v.pos.y, v.pos.z));
/* 225 */           points.add(new Vec4(v.pos.x, eb, v.pos.z));
/* 226 */           points.add(new Vec4(v.pos.x + fx * 2.0D, v.pos.y, v.pos.z + fz * 2.0D));
/* 227 */           points.add(new Vec4(v.pos.x + fx * 2.0D, eb, v.pos.z + fz * 2.0D));
/*     */         }
/*     */       }
/*     */       
/* 231 */       for (int q = 0; q < mesh.nodes.size(); q++)
/*     */       {
/* 233 */         if (i != q) {
/* 234 */           ClothNode u = (ClothNode)mesh.nodes.get(q);
/*     */           
/* 236 */           Vec4 d = v.pos.subtract3(u.pos);
/* 237 */           double distance = d.getLength3() + 0.1D;
/* 238 */           Vec4 direction = d.normalize3();
/*     */           
/* 240 */           v.applyForce(direction.multiply3(repulsion).divide3(distance * distance * 0.5D));
/* 241 */           u.applyForce(direction.multiply3(repulsion).divide3(distance * distance * -0.5D));
/*     */         } }
/*     */     }
/* 244 */     ClothNode v = null;
/* 245 */     ClothNode u = null;
/*     */     
/*     */ 
/* 248 */     for (NodeLink link : mesh.links) {
/* 249 */       v = (ClothNode)mesh.nodes.get(link.a);
/* 250 */       u = (ClothNode)mesh.nodes.get(link.b);
/* 251 */       if ((u != null) && (v != null)) {
/* 252 */         Vec4 d = u.pos.subtract3(v.pos);
/* 253 */         double displacement = link.length - d.getLength3();
/* 254 */         Vec4 direction = d.normalize3();
/*     */         
/* 256 */         v.applyForce(direction.multiply3(stiffness * stiffness * displacement * displacement * 0.5D));
/* 257 */         u.applyForce(direction.multiply3(stiffness * stiffness * displacement * displacement * -0.5D));
/*     */       }
/*     */     }
/* 260 */     this.body = Box.computeBoundingBox(points);
/*     */     
/*     */ 
/* 263 */     for (ClothNode n : mesh.nodes)
/*     */     {
/* 265 */       n.acc = n.acc.add3(0.0D, n.g * timestep, 0.0D);
/* 266 */       n.vel = n.vel.add3(n.acc.multiply3(timestep)).multiply3(damping);
/* 267 */       n.acc = new Vec4(0.0D, 0.0D, 0.0D);
/* 268 */       if (!n.fixed) {
/* 269 */         n.pos = n.pos.add3(n.vel.multiply3(timestep));
/* 270 */         doCollisions(mesh, n, entity);
/*     */       }
/*     */     }
/*     */     
/* 274 */     mesh.lastCorners = this.body.getCorners();
/*     */   }
/*     */   
/*     */   private void doCollisions(ClothMesh mesh, ClothNode node, EntityLivingBase entity)
/*     */   {
/* 279 */     Line line = new Line(node.pos, node.vel);
/* 280 */     Intersection[] is = this.body.intersect(line);
/* 281 */     if ((is != null) && (is.length == 1) && (mesh.lastCorners != null)) {
/* 282 */       Vec4[] bc = this.body.getCorners();
/* 283 */       double dist = Double.MAX_VALUE;
/* 284 */       int c = -1;
/* 285 */       for (int ic = 0; ic < mesh.lastCorners.length; ic++) {
/* 286 */         if (!mesh.lastCorners[ic].equals(bc[ic])) {
/* 287 */           double d = node.pos.distanceToSquared3(bc[ic]);
/* 288 */           if (d < dist) {
/* 289 */             dist = d;
/* 290 */             c = ic;
/*     */           }
/*     */         }
/*     */       }
/* 294 */       if (c >= 0) {
/* 295 */         Line line2 = new Line(node.pos, bc[c].subtract3(mesh.lastCorners[c]));
/* 296 */         Intersection[] is2 = this.body.intersect(line2);
/* 297 */         if ((is2 != null) && (is2.length == 1))
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/* 302 */           node.pos = is2[0].getIntersectionPoint();
/* 303 */           node.vel = new Vec4(0.0D, 0.0D, 0.0D);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 308 */     BlockPos bp = new BlockPos(node.pos.x, node.pos.y, node.pos.z);
/*     */     
/* 310 */     List<AxisAlignedBB> list = entity.worldObj.func_147461_a(new AxisAlignedBB(node.pos.x, node.pos.y, node.pos.z, node.pos.x, node.pos.y, node.pos.z).expand(0.1D, 0.1D, 0.1D));
/*     */     
/*     */ 
/* 313 */     if ((!list.isEmpty()) || (entity.worldObj.isBlockFullCube(bp))) {
/* 314 */       IBlockState block = entity.worldObj.getBlockState(bp);
/* 315 */       AxisAlignedBB bb = block.getBlock().getCollisionBoundingBox(entity.worldObj, bp, block);
/* 316 */       Vec3 vv = pushOutOfBB(entity.worldObj, node.pos.x, node.pos.y, node.pos.z, bb);
/* 317 */       if (vv != null) {
/* 318 */         node.pos = new Vec4(vv.xCoord, vv.yCoord, vv.zCoord);
/* 319 */         node.vel = new Vec4(0.0D, 0.0D, 0.0D);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private Vec3 pushOutOfBB(World worldObj, double x, double y, double z, AxisAlignedBB bb)
/*     */   {
/* 328 */     double mX = x;
/* 329 */     double mY = y;
/* 330 */     double mZ = z;
/* 331 */     BlockPos blockpos = new BlockPos(x, y, z);
/* 332 */     double d0 = x - blockpos.getX();
/* 333 */     double d1 = y - blockpos.getY();
/* 334 */     double d2 = z - blockpos.getZ();
/*     */     
/* 336 */     if (bb == null) return null;
/* 337 */     int i = 3;
/* 338 */     double d3 = 9999.0D;
/*     */     
/* 340 */     if ((!worldObj.isBlockFullCube(blockpos.west())) && (d0 < d3))
/*     */     {
/* 342 */       d3 = d0;
/* 343 */       i = 0;
/*     */     }
/*     */     
/* 346 */     if ((!worldObj.isBlockFullCube(blockpos.east())) && (1.0D - d0 < d3))
/*     */     {
/* 348 */       d3 = 1.0D - d0;
/* 349 */       i = 1;
/*     */     }
/*     */     
/* 352 */     if ((!worldObj.isBlockFullCube(blockpos.up())) && (1.0D - d1 < d3))
/*     */     {
/* 354 */       d3 = 1.0D - d1;
/* 355 */       i = 3;
/*     */     }
/*     */     
/* 358 */     if ((!worldObj.isBlockFullCube(blockpos.north())) && (d2 < d3))
/*     */     {
/* 360 */       d3 = d2;
/* 361 */       i = 4;
/*     */     }
/*     */     
/* 364 */     if ((!worldObj.isBlockFullCube(blockpos.south())) && (1.0D - d2 < d3))
/*     */     {
/* 366 */       d3 = 1.0D - d2;
/* 367 */       i = 5;
/*     */     }
/*     */     
/* 370 */     float f = worldObj.rand.nextFloat() * 0.2F + 0.1F;
/*     */     
/* 372 */     if (i == 0)
/*     */     {
/* 374 */       mX = bb.minX - 0.01D;
/*     */     }
/*     */     
/* 377 */     if (i == 1)
/*     */     {
/* 379 */       mX = bb.maxX + 0.01D;
/*     */     }
/*     */     
/* 382 */     if (i == 3)
/*     */     {
/* 384 */       mY = bb.maxY + 0.01D;
/*     */     }
/*     */     
/* 387 */     if (i == 4)
/*     */     {
/* 389 */       mZ = bb.minZ - 0.01D;
/*     */     }
/*     */     
/* 392 */     if (i == 5)
/*     */     {
/* 394 */       mZ = bb.maxZ + 0.01D;
/*     */     }
/*     */     
/* 397 */     return new Vec3(mX, mY, mZ);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 406 */   HashMap<Integer, Long> lastRender = new HashMap();
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   @SubscribeEvent
/*     */   public void renderLivingPost(RenderLivingEvent.Post event) {
/* 411 */     if (!(event.entity instanceof EntityVillager)) { return;
/*     */     }
/* 413 */     long time = System.currentTimeMillis();
/*     */     
/* 415 */     GlStateManager.pushMatrix();
/* 416 */     GlStateManager.translate(-event.renderer.getRenderManager().viewerPosX, -event.renderer.getRenderManager().viewerPosY, -event.renderer.getRenderManager().viewerPosZ);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 421 */     if ((!this.lastRender.containsKey(Integer.valueOf(event.entity.getEntityId()))) || (((Long)this.lastRender.get(Integer.valueOf(event.entity.getEntityId()))).longValue() < time))
/*     */     {
/*     */ 
/* 424 */       processCapeTick(event.entity, 1.0F);
/* 425 */       this.lastRender.put(Integer.valueOf(event.entity.getEntityId()), Long.valueOf(time + 20L));
/*     */       
/* 427 */       if (this.body != null) {
/* 428 */         for (Vec4 corner : this.body.getCorners()) {
/* 429 */           Thaumcraft.proxy.getFX().ssparkle((float)corner.x, (float)corner.y, (float)corner.z, 1);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 434 */     ClothMesh mc = (ClothMesh)this.clothMeshes.get(Integer.valueOf(event.entity.getEntityId()));
/* 435 */     if (mc == null) { return;
/*     */     }
/*     */     
/*     */ 
/* 439 */     for (NodeLink link : mc.links)
/*     */     {
/* 441 */       ClothNode vc = (ClothNode)mc.nodes.get(link.a);
/* 442 */       ClothNode uc = (ClothNode)mc.nodes.get(link.b);
/*     */       
/* 444 */       drawLine(vc.pos.x, vc.pos.y, vc.pos.z, uc.pos.x, uc.pos.y, uc.pos.z);
/*     */     }
/*     */     
/*     */ 
/* 448 */     GlStateManager.popMatrix();
/*     */   }
/*     */   
/*     */   private double interpolateValue(double start, double end, double pct)
/*     */   {
/* 453 */     return start + (end - start) * pct;
/*     */   }
/*     */   
/*     */   private void drawLine(double x, double y, double z, double x2, double y2, double z2) {
/* 457 */     Tessellator var12 = Tessellator.getInstance();
/* 458 */     GL11.glPushMatrix();
/* 459 */     GL11.glLineWidth(2.0F);
/* 460 */     GL11.glDisable(3553);
/* 461 */     GL11.glBlendFunc(770, 1);
/* 462 */     var12.getWorldRenderer().begin(3, net.minecraft.client.renderer.vertex.DefaultVertexFormats.POSITION_COLOR);
/* 463 */     var12.getWorldRenderer().pos(x, y, z).color(0.0F, 0.6F, 0.8F, 1.0F).endVertex();
/* 464 */     var12.getWorldRenderer().pos(x2, y2, z2).color(0.0F, 0.6F, 0.8F, 1.0F).endVertex();
/* 465 */     var12.draw();
/* 466 */     GL11.glBlendFunc(770, 771);
/* 467 */     GL11.glDisable(32826);
/* 468 */     GL11.glEnable(3553);
/* 469 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 470 */     GL11.glPopMatrix();
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\lib\ClothHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */