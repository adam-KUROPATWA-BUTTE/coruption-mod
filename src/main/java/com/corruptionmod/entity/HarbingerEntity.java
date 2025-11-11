package com.corruptionmod.entity;

import com.corruptionmod.ModEntities;
import com.corruptionmod.entity.corrupted.CorruptedSpiderEntity;
import com.corruptionmod.entity.corrupted.CorruptedZombieEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

/**
 * The Harbinger of Entropy - Final Boss Entity
 * Three phase boss fight with varied attacks and minion summoning
 */
public class HarbingerEntity extends HostileEntity implements GeoEntity {
    
    // Boss phases
    public static final int PHASE_1 = 1; // 100%-66% HP
    public static final int PHASE_2 = 2; // 66%-33% HP
    public static final int PHASE_3 = 3; // 33%-0% HP
    
    // Tracked data
    private static final TrackedData<Integer> PHASE = DataTracker.registerData(HarbingerEntity.class, TrackedDataHandlerRegistry.INTEGER);
    
    // Boss bar
    private final ServerBossBar bossBar;
    
    // Combat timers
    private int voidBoltCooldown = 0;
    private int tentacleSlamCooldown = 0;
    private int minionSummonCooldown = 0;
    private int voidPulseCooldown = 0;
    private int teleportStrikeCooldown = 0;
    private int realityTearCooldown = 0;
    private int voidCascadeCooldown = 0;
    
    // Animation cache
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    
    public HarbingerEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 500; // Large XP reward
        
        // Create boss bar
        this.bossBar = new ServerBossBar(
            Text.translatable("entity.corruptionmod.harbinger"),
            BossBar.Color.PURPLE,
            BossBar.Style.NOTCHED_10
        );
        this.bossBar.setDarkenSky(true);
    }
    
    public static DefaultAttributeContainer.Builder createHarbingerAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 500.0) // 250 hearts
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 15.0) // 7.5 hearts
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.5)
                .add(EntityAttributes.GENERIC_ARMOR, 10.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 64.0);
    }
    
    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 0.8));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 16.0f));
        this.goalSelector.add(4, new LookAroundGoal(this));
        
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(2, new RevengeTargetGoal(this));
    }
    
    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(PHASE, PHASE_1);
    }
    
    public int getPhase() {
        return this.dataTracker.get(PHASE);
    }
    
    public void setPhase(int phase) {
        this.dataTracker.set(PHASE, phase);
        updateBossBar();
        onPhaseChange(phase);
    }
    
    private void updateBossBar() {
        int phase = getPhase();
        String phaseName = switch (phase) {
            case PHASE_1 -> "Phase 1";
            case PHASE_2 -> "Phase 2 - Enraged";
            case PHASE_3 -> "Phase 3 - Reality Shattered";
            default -> "Unknown";
        };
        
        this.bossBar.setName(Text.translatable("entity.corruptionmod.harbinger")
            .append(Text.literal(" - " + phaseName)));
        
        // Change boss bar color based on phase
        switch (phase) {
            case PHASE_1 -> this.bossBar.setColor(BossBar.Color.PURPLE);
            case PHASE_2 -> this.bossBar.setColor(BossBar.Color.RED);
            case PHASE_3 -> this.bossBar.setColor(BossBar.Color.PINK);
        }
    }
    
    private void onPhaseChange(int newPhase) {
        // Play phase transition animation and effects
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            // Spawn dramatic particles
            for (int i = 0; i < 50; i++) {
                double offsetX = (this.random.nextDouble() - 0.5) * 3;
                double offsetY = this.random.nextDouble() * 2;
                double offsetZ = (this.random.nextDouble() - 0.5) * 3;
                
                serverWorld.spawnParticles(
                    ParticleTypes.PORTAL,
                    this.getX() + offsetX,
                    this.getY() + offsetY,
                    this.getZ() + offsetZ,
                    1,
                    0, 0, 0,
                    0.1
                );
            }
            
            // Play sound
            this.playSound(SoundEvents.ENTITY_WITHER_SPAWN, 2.0f, 0.8f);
        }
        
        // Adjust stats based on phase
        switch (newPhase) {
            case PHASE_2 -> {
                // 20% speed boost
                this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)
                    .setBaseValue(0.36);
            }
            case PHASE_3 -> {
                // 30% speed boost total, increased knockback resistance
                this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)
                    .setBaseValue(0.39);
                this.getAttributeInstance(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE)
                    .setBaseValue(0.8);
            }
        }
    }
    
    @Override
    public void tick() {
        super.tick();
        
        // Update phase based on health
        float healthPercentage = this.getHealth() / this.getMaxHealth();
        int currentPhase = getPhase();
        
        if (healthPercentage <= 0.33f && currentPhase < PHASE_3) {
            setPhase(PHASE_3);
        } else if (healthPercentage <= 0.66f && currentPhase < PHASE_2) {
            setPhase(PHASE_2);
        }
        
        // Update boss bar
        this.bossBar.setPercent(healthPercentage);
        
        if (!this.getWorld().isClient()) {
            // Decrement cooldowns
            if (voidBoltCooldown > 0) voidBoltCooldown--;
            if (tentacleSlamCooldown > 0) tentacleSlamCooldown--;
            if (minionSummonCooldown > 0) minionSummonCooldown--;
            if (voidPulseCooldown > 0) voidPulseCooldown--;
            if (teleportStrikeCooldown > 0) teleportStrikeCooldown--;
            if (realityTearCooldown > 0) realityTearCooldown--;
            if (voidCascadeCooldown > 0) voidCascadeCooldown--;
            
            // Execute phase-specific AI
            executePhaseAI();
            
            // Spawn ambient particles
            spawnAmbientParticles();
        }
    }
    
    private void executePhaseAI() {
        PlayerEntity target = this.getWorld().getClosestPlayer(this, 64.0);
        if (target == null || !this.canSee(target)) {
            return;
        }
        
        int phase = getPhase();
        
        // Phase 1 abilities
        if (phase >= PHASE_1) {
            if (voidBoltCooldown <= 0 && this.random.nextInt(100) < 10) {
                castVoidBolt(target);
                voidBoltCooldown = 60; // 3 seconds
            }
            
            if (tentacleSlamCooldown <= 0 && this.squaredDistanceTo(target) < 25) {
                performTentacleSlam();
                tentacleSlamCooldown = 80; // 4 seconds
            }
            
            if (minionSummonCooldown <= 0) {
                summonMinions(2, 0);
                minionSummonCooldown = 600; // 30 seconds
            }
        }
        
        // Phase 2 abilities
        if (phase >= PHASE_2) {
            if (teleportStrikeCooldown <= 0 && this.random.nextInt(100) < 8) {
                performTeleportStrike(target);
                teleportStrikeCooldown = 100; // 5 seconds
            }
            
            if (voidPulseCooldown <= 0) {
                castVoidPulse();
                voidPulseCooldown = 300; // 15 seconds
            }
            
            // Enhanced summoning for phase 2
            if (minionSummonCooldown <= 0) {
                summonMinions(3, 1);
                minionSummonCooldown = 500; // 25 seconds
            }
        }
        
        // Phase 3 abilities
        if (phase >= PHASE_3) {
            if (realityTearCooldown <= 0 && this.random.nextInt(100) < 12) {
                createRealityTear(target);
                realityTearCooldown = 120; // 6 seconds
            }
            
            if (voidCascadeCooldown <= 0) {
                unleashVoidCascade();
                voidCascadeCooldown = 400; // 20 seconds
            }
            
            // Continuous summoning in phase 3
            if (minionSummonCooldown <= 0) {
                summonMinions(2, 1);
                minionSummonCooldown = 400; // 20 seconds
            }
        }
    }
    
    private void castVoidBolt(PlayerEntity target) {
        // TODO: Implement Void Bolt projectile
        Vec3d direction = target.getPos().subtract(this.getPos()).normalize();
        
        // Play cast animation and sound
        this.playSound(SoundEvents.ENTITY_BLAZE_SHOOT, 1.0f, 0.8f);
        
        // Spawn particle effect
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(
                ParticleTypes.WITCH,
                this.getX(),
                this.getY() + 1.5,
                this.getZ(),
                10,
                0.2, 0.2, 0.2,
                0.1
            );
        }
    }
    
    private void performTentacleSlam() {
        // AOE attack in 5-block radius
        BlockPos center = this.getBlockPos();
        
        this.getWorld().getEntitiesByClass(PlayerEntity.class, this.getBoundingBox().expand(5.0), 
            player -> player.squaredDistanceTo(this) < 25
        ).forEach(player -> {
            player.damage(this.getDamageSources().mobAttack(this), 10.0f);
            player.takeKnockback(1.0, this.getX() - player.getX(), this.getZ() - player.getZ());
        });
        
        // Play sound and effects
        this.playSound(SoundEvents.ENTITY_RAVAGER_ROAR, 2.0f, 0.6f);
        
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            for (int i = 0; i < 30; i++) {
                double angle = (i / 30.0) * Math.PI * 2;
                double radius = 5.0;
                serverWorld.spawnParticles(
                    ParticleTypes.EXPLOSION,
                    this.getX() + Math.cos(angle) * radius,
                    this.getY(),
                    this.getZ() + Math.sin(angle) * radius,
                    1,
                    0, 0, 0,
                    0
                );
            }
        }
    }
    
    private void summonMinions(int zombies, int spiders) {
        if (!(this.getWorld() instanceof ServerWorld serverWorld)) {
            return;
        }
        
        for (int i = 0; i < zombies; i++) {
            CorruptedZombieEntity zombie = new CorruptedZombieEntity(ModEntities.CORRUPTED_ZOMBIE, this.getWorld());
            
            // Spawn near boss
            double offsetX = (this.random.nextDouble() - 0.5) * 8;
            double offsetZ = (this.random.nextDouble() - 0.5) * 8;
            zombie.refreshPositionAndAngles(
                this.getX() + offsetX,
                this.getY(),
                this.getZ() + offsetZ,
                this.random.nextFloat() * 360,
                0
            );
            
            serverWorld.spawnEntity(zombie);
            serverWorld.spawnParticles(ParticleTypes.PORTAL, zombie.getX(), zombie.getY(), zombie.getZ(), 20, 0.5, 1, 0.5, 0.1);
        }
        
        for (int i = 0; i < spiders; i++) {
            CorruptedSpiderEntity spider = new CorruptedSpiderEntity(ModEntities.CORRUPTED_SPIDER, this.getWorld());
            
            double offsetX = (this.random.nextDouble() - 0.5) * 8;
            double offsetZ = (this.random.nextDouble() - 0.5) * 8;
            spider.refreshPositionAndAngles(
                this.getX() + offsetX,
                this.getY(),
                this.getZ() + offsetZ,
                this.random.nextFloat() * 360,
                0
            );
            
            serverWorld.spawnEntity(spider);
            serverWorld.spawnParticles(ParticleTypes.PORTAL, spider.getX(), spider.getY(), spider.getZ(), 20, 0.5, 1, 0.5, 0.1);
        }
        
        this.playSound(SoundEvents.ENTITY_EVOKER_CAST_SPELL, 1.5f, 0.7f);
    }
    
    private void performTeleportStrike(PlayerEntity target) {
        // Teleport behind player
        Vec3d targetPos = target.getPos();
        Vec3d lookVec = target.getRotationVector().normalize().multiply(-2);
        Vec3d teleportPos = targetPos.add(lookVec);
        
        // Spawn particles at old location
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(ParticleTypes.PORTAL, this.getX(), this.getY(), this.getZ(), 30, 0.5, 1, 0.5, 0.1);
        }
        
        this.teleport(teleportPos.x, teleportPos.y, teleportPos.z);
        
        // Spawn particles at new location
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(ParticleTypes.PORTAL, this.getX(), this.getY(), this.getZ(), 30, 0.5, 1, 0.5, 0.1);
        }
        
        // Immediately attack
        this.tryAttack(target);
        
        this.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0f, 0.8f);
    }
    
    private void castVoidPulse() {
        // AOE damage wave
        this.getWorld().getEntitiesByClass(PlayerEntity.class, this.getBoundingBox().expand(10.0),
            player -> true
        ).forEach(player -> {
            double distance = player.squaredDistanceTo(this);
            float damage = (float) (12.0 - (distance / 10.0)); // Damage decreases with distance
            player.damage(this.getDamageSources().magic(), Math.max(damage, 2.0f));
        });
        
        this.playSound(SoundEvents.ENTITY_WITHER_SHOOT, 2.0f, 0.5f);
        
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            for (int ring = 1; ring <= 3; ring++) {
                for (int i = 0; i < 20 * ring; i++) {
                    double angle = (i / (20.0 * ring)) * Math.PI * 2;
                    double radius = ring * 3.0;
                    serverWorld.spawnParticles(
                        ParticleTypes.DRAGON_BREATH,
                        this.getX() + Math.cos(angle) * radius,
                        this.getY() + 0.5,
                        this.getZ() + Math.sin(angle) * radius,
                        1,
                        0, 0, 0,
                        0
                    );
                }
            }
        }
    }
    
    private void createRealityTear(PlayerEntity target) {
        // TODO: Implement Reality Tear entity/effect
        BlockPos tearPos = target.getBlockPos().add(
            (this.random.nextInt(5) - 2),
            0,
            (this.random.nextInt(5) - 2)
        );
        
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            // Create damage zone
            for (int tick = 0; tick < 100; tick++) {
                serverWorld.spawnParticles(
                    ParticleTypes.WITCH,
                    tearPos.getX() + 0.5,
                    tearPos.getY() + 1,
                    tearPos.getZ() + 0.5,
                    2,
                    0.5, 1, 0.5,
                    0.05
                );
            }
        }
        
        this.playSound(SoundEvents.BLOCK_PORTAL_AMBIENT, 1.5f, 0.6f);
    }
    
    private void unleashVoidCascade() {
        // Massive AOE attack
        this.getWorld().getEntitiesByClass(PlayerEntity.class, this.getBoundingBox().expand(15.0),
            player -> true
        ).forEach(player -> {
            player.damage(this.getDamageSources().magic(), 20.0f);
            player.takeKnockback(2.0, this.getX() - player.getX(), this.getZ() - player.getZ());
        });
        
        this.playSound(SoundEvents.ENTITY_WITHER_DEATH, 2.0f, 0.4f);
        
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            // Create massive particle explosion
            for (int i = 0; i < 100; i++) {
                double offsetX = (this.random.nextDouble() - 0.5) * 15;
                double offsetY = this.random.nextDouble() * 3;
                double offsetZ = (this.random.nextDouble() - 0.5) * 15;
                
                serverWorld.spawnParticles(
                    ParticleTypes.EXPLOSION_EMITTER,
                    this.getX() + offsetX,
                    this.getY() + offsetY,
                    this.getZ() + offsetZ,
                    1,
                    0, 0, 0,
                    0
                );
            }
        }
    }
    
    private void spawnAmbientParticles() {
        if (this.random.nextInt(2) == 0 && this.getWorld() instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(
                ParticleTypes.PORTAL,
                this.getX() + (this.random.nextDouble() - 0.5) * this.getWidth(),
                this.getY() + this.random.nextDouble() * this.getHeight(),
                this.getZ() + (this.random.nextDouble() - 0.5) * this.getWidth(),
                1,
                0, 0.05, 0,
                0.02
            );
        }
    }
    
    @Override
    public void onStartedTrackingBy(ServerPlayerEntity player) {
        super.onStartedTrackingBy(player);
        this.bossBar.addPlayer(player);
    }
    
    @Override
    public void onStoppedTrackingBy(ServerPlayerEntity player) {
        super.onStoppedTrackingBy(player);
        this.bossBar.removePlayer(player);
    }
    
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("Phase")) {
            this.setPhase(nbt.getInt("Phase"));
        }
        if (this.hasCustomName()) {
            this.bossBar.setName(this.getDisplayName());
        }
    }
    
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Phase", this.getPhase());
    }
    
    @Override
    public void setCustomName(Text name) {
        super.setCustomName(name);
        this.bossBar.setName(this.getDisplayName());
    }
    
    @Override
    protected void updatePostDeath() {
        super.updatePostDeath();
        // Clean up boss bar
        this.bossBar.clearPlayers();
    }
    
    // GeckoLib Animation Implementation
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }
    
    private PlayState predicate(AnimationState<HarbingerEntity> event) {
        if (this.isDead()) {
            event.getController().setAnimation(RawAnimation.begin().then("death", Animation.LoopType.PLAY_ONCE));
            return PlayState.CONTINUE;
        }
        
        if (this.handSwinging) {
            event.getController().setAnimation(RawAnimation.begin().then("attack", Animation.LoopType.PLAY_ONCE));
            return PlayState.CONTINUE;
        }
        
        if (event.isMoving()) {
            event.getController().setAnimation(RawAnimation.begin().then("walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        
        event.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }
    
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
