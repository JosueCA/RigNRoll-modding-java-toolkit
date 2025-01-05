/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import rnrcore.SCRanimparts;
import rnrcore.SCRperson;
import rnrcore.eng;
import rnrcore.vectorJ;
import rnrscr.Chainanm;
import rnrscr.animation;

public class SFpedestrians
extends animation {
    private static final String MODEL_TO_CHOOSE = null;
    static SCRanimparts MANGO1;
    static SCRanimparts JUMP002;
    static SCRanimparts JUMP002Sim;
    static SCRanimparts OTHOD2;
    static SCRanimparts Falling;
    static SCRanimparts FallingSim;
    static SCRanimparts Laing;
    static SCRanimparts BLaing;
    static SCRanimparts StandUp;
    static SCRanimparts StandUpBack;
    static SCRanimparts FRONTFALL;
    static SCRanimparts BACKFALL;
    static SCRanimparts ToWallB1;
    static SCRanimparts ToWallB2;
    static SCRanimparts ToWallF1;
    static SCRanimparts ATAWALL;
    static SCRanimparts ATAWALLFACE;
    static SCRanimparts ASINCH1;
    static SCRanimparts OTHODB;
    static SCRanimparts STANDING;
    static SCRanimparts FORCAR1;
    static SCRanimparts FORCAR2;
    static SCRanimparts FORCAR3;
    static SCRanimparts FORCAR4;
    static SCRanimparts FORCAR5;
    static SCRanimparts FORCAR6;
    static SCRanimparts FORCAR7;
    static SCRanimparts FORCAR8;
    static SCRanimparts FORCAR9;
    static SCRanimparts FORCAR9SIM;
    static SCRanimparts FORCAR10;
    static SCRanimparts FORCAR10SIM;
    static SCRanimparts RUN;
    static SCRperson PERSONAGE;

    public void BlockedByCar(SCRperson PERSONAGEIn) {
        Chainanm FirstChain = this.CreateChain(PERSONAGEIn);
        FirstChain.Add(3, 5, 1, 2.0);
        FirstChain.AddAsk(7, 1.0);
        FirstChain.Add(3, 3, 1, 0.1);
        PERSONAGEIn.StartChain(FirstChain);
    }

    public void RunFrom(SCRperson PERSONAGEIn) {
        Chainanm FirstChain = this.CreateChain(PERSONAGEIn);
        FirstChain.Add(3, 4, 1, SFpedestrians.RandomLength(1.0, 2.0));
        PERSONAGEIn.StartChain(FirstChain);
    }

    public void FalltoFront(SCRperson PERSONAGEIn) {
        Chainanm FirstChain = this.CreateChain(PERSONAGEIn);
        FirstChain.Add(2, 1, 7);
        FirstChain.Add(3, 5, 3, 2.0);
        FirstChain.AddAsk(6, 1.0);
        FirstChain.Add(2, 1, 10);
        PERSONAGEIn.StartChain(FirstChain);
    }

    public void FalltoBack(SCRperson PERSONAGEIn) {
        Chainanm FirstChain = this.CreateChain(PERSONAGEIn);
        FirstChain.Add(2, 1, 8);
        FirstChain.Add(3, 5, 2, 2.0);
        FirstChain.AddAsk(6, 1.0);
        FirstChain.Add(2, 1, 9);
        PERSONAGEIn.StartChain(FirstChain);
    }

    public void FlightOverChest(SCRperson PERSONAGEIn) {
        Chainanm FirstChain = this.CreateChain(PERSONAGEIn);
        FirstChain.Add(2, 1, 13);
        FirstChain.Add(3, 5, 3, 2.0);
        FirstChain.AddAsk(6, 1.0);
        FirstChain.Add(2, 1, 10);
        PERSONAGEIn.StartChain(FirstChain);
    }

    public void FlightOverBack(SCRperson PERSONAGEIn) {
        Chainanm FirstChain = this.CreateChain(PERSONAGEIn);
        FirstChain.Add(2, 1, 14);
        FirstChain.Add(3, 5, 2, 2.0);
        FirstChain.AddAsk(6, 1.0);
        FirstChain.Add(2, 1, 9);
        PERSONAGEIn.StartChain(FirstChain);
    }

    public void ReturnOnWay(SCRperson PERSONAGEIn, vectorJ toreturn) {
        Chainanm FirstChain = this.CreateChain(PERSONAGEIn);
        FirstChain.Add(1, toreturn, 3, 1);
        PERSONAGEIn.StartChain(FirstChain);
    }

    public void LeftOthod(SCRperson PERSONAGEIn) {
        Chainanm FirstChain = this.CreateChain(PERSONAGEIn);
        FirstChain.Add(2, 1, 11);
        PERSONAGEIn.StartChain(FirstChain);
    }

    public void RaunToWall(SCRperson PERSONAGEIn, vectorJ point1, vectorJ point2) {
        Chainanm FirstChain = this.CreateChain(PERSONAGEIn);
        FirstChain.Add(1, point1, 4, 1);
        FirstChain.Add(1, point2, 4, 1);
        FirstChain.Add(2, 1, 2);
        FirstChain.Add(3, 2, 2, 10.0);
        PERSONAGEIn.StartChain(FirstChain);
    }

    public void FallRight(SCRperson PERSONAGEIn) {
        Chainanm FirstChain = this.CreateChain(PERSONAGEIn);
        FirstChain.Add(2, 1, 5);
        FirstChain.Add(3, 5, 3, 2.0);
        FirstChain.AddAsk(6, 1.0);
        FirstChain.Add(2, 1, 10);
        PERSONAGEIn.StartChain(FirstChain);
    }

    public void FallLeft(SCRperson PERSONAGEIn) {
        Chainanm FirstChain = this.CreateChain(PERSONAGEIn);
        FirstChain.Add(2, 1, 6);
        FirstChain.Add(3, 5, 3, 2.0);
        FirstChain.AddAsk(6, 1.0);
        FirstChain.Add(2, 1, 10);
        PERSONAGEIn.StartChain(FirstChain);
    }

    public void PlayPredefine() {
        Chainanm FirstChain = this.CreateChain(PERSONAGE);
        FirstChain.Add(2, 1, 3);
        FirstChain.Add(2, 1, 4);
        FirstChain.Add(2, 1, 8);
        FirstChain.Add(3, 5, 2, 1.0);
        FirstChain.Add(2, 1, 9);
        FirstChain.Add(2, 1, 7);
        FirstChain.Add(3, 5, 3, 2.0);
        FirstChain.Add(2, 1, 10);
        FirstChain.Add(3, 3, 1);
        PERSONAGE.StartChain(FirstChain);
    }

    public void Init(int VsegoPed) {
        if (VsegoPed == 0) {
            return;
        }
        CreateAnimation animcreation = new CreateAnimation();
        Eventsmain EVENTS = new Eventsmain();
        for (int i = 0; i < VsegoPed; ++i) {
            animcreation.dotisthin(animcreation.getModelName(i));
            EVENTS.dotisthin(PERSONAGE);
        }
    }

    public static SCRperson createPedestrian(String model_name) {
        SFpedestrians sf = new SFpedestrians();
        CreateAnimation animcreation = sf.new CreateAnimation();
        Eventsmain EVENTS = sf.new Eventsmain();
        animcreation.dotisthin(model_name);
        EVENTS.dotisthin(PERSONAGE);
        return PERSONAGE;
    }

    public class Eventsmain {
        public void dotisthin(SCRperson PERSONAGEIn) {
            PERSONAGEIn.AttachToEvent(7);
            PERSONAGEIn.AttachToEvent(5);
            PERSONAGEIn.AttachToEvent(4);
            PERSONAGEIn.AttachToEvent(6);
        }
    }

    public class CreateAnimation {
        private boolean isWomanModel(String model_name) {
            return model_name.startsWith("Woman");
        }

        private boolean isHeelWomanModel(String model_name) {
            return model_name.compareTo("Woman007") == 0;
        }

        private String getModelName(int nom) {
            String model_name = "Woman001";
            if (++nom > 41) {
                double deveance = (double)nom / 41.0;
                int periodF = (int)Math.floor(deveance);
                nom -= 41 * periodF;
            }
            switch (nom) {
                case 0: {
                    model_name = "Woman001";
                    break;
                }
                case 1: {
                    model_name = "Woman001";
                    break;
                }
                case 2: {
                    model_name = "Woman002";
                    break;
                }
                case 3: {
                    model_name = "Woman003";
                    break;
                }
                case 4: {
                    model_name = "Woman004";
                    break;
                }
                case 5: {
                    model_name = "Woman005";
                    break;
                }
                case 6: {
                    model_name = "Woman006";
                    break;
                }
                case 7: {
                    model_name = "Woman007";
                    break;
                }
                case 8: {
                    model_name = "Woman008";
                    break;
                }
                case 9: {
                    model_name = "Woman009";
                    break;
                }
                case 10: {
                    model_name = "Woman010";
                    break;
                }
                case 11: {
                    model_name = "Woman011";
                    break;
                }
                case 12: {
                    model_name = "Woman012";
                    break;
                }
                case 13: {
                    model_name = "Woman013";
                    break;
                }
                case 14: {
                    model_name = "Woman014";
                    break;
                }
                case 15: {
                    model_name = "Woman015";
                    break;
                }
                case 16: {
                    model_name = "Woman016";
                    break;
                }
                case 17: {
                    model_name = "Woman017";
                    break;
                }
                case 18: {
                    model_name = "Woman018";
                    break;
                }
                case 19: {
                    model_name = "Woman019";
                    break;
                }
                case 20: {
                    model_name = "Woman020";
                    break;
                }
                case 21: {
                    model_name = "Man_001";
                    break;
                }
                case 22: {
                    model_name = "Man_002";
                    break;
                }
                case 23: {
                    model_name = "Man_003";
                    break;
                }
                case 24: {
                    model_name = "Man_004";
                    break;
                }
                case 25: {
                    model_name = "Man_005";
                    break;
                }
                case 26: {
                    model_name = "Man_006";
                    break;
                }
                case 27: {
                    model_name = "Man_007";
                    break;
                }
                case 28: {
                    model_name = "Man_008";
                    break;
                }
                case 29: {
                    model_name = "Man_009";
                    break;
                }
                case 30: {
                    model_name = "Man_010";
                    break;
                }
                case 31: {
                    model_name = "Man_011";
                    break;
                }
                case 32: {
                    model_name = "Man_012";
                    break;
                }
                case 33: {
                    model_name = "Man_013";
                    break;
                }
                case 34: {
                    model_name = "Man_014";
                    break;
                }
                case 35: {
                    model_name = "Man_015";
                    break;
                }
                case 36: {
                    model_name = "Man_016";
                    break;
                }
                case 37: {
                    model_name = "Man_017";
                    break;
                }
                case 38: {
                    model_name = "Man_018";
                    break;
                }
                case 39: {
                    model_name = "Man_019";
                    break;
                }
                case 40: {
                    model_name = "Man_020";
                    break;
                }
                default: {
                    model_name = "Man_001";
                }
            }
            if (null != MODEL_TO_CHOOSE) {
                model_name = MODEL_TO_CHOOSE;
            }
            return model_name;
        }

        public void dotisthin(String model_name) {
            String NameOfRun;
            String NameOfWalk;
            vectorJ temppos = new vectorJ();
            vectorJ pos = new vectorJ(0.0, 0.0, 0.0);
            vectorJ dir = new vectorJ(0.0, 1.0, 0.0);
            boolean iswoman = this.isWomanModel(model_name);
            int WOMANHEEL = 1;
            int WOMANFOOT = 2;
            int womantype = iswoman ? (this.isHeelWomanModel(model_name) ? WOMANHEEL : WOMANFOOT) : 0;
            PERSONAGE = SCRperson.CreateAnm(model_name, pos, dir, false);
            double tuner = 1.0;
            double tunerFall = 2.0;
            int Choice = 0;
            double randCH = Math.random();
            if (randCH < 0.25) {
                Choice = 1;
            }
            if (randCH >= 0.25 && randCH < 0.5) {
                Choice = 2;
            }
            if (randCH >= 0.5 && randCH < 0.75) {
                Choice = 3;
            }
            if (randCH >= 0.75 && randCH < 1.0) {
                Choice = 4;
            }
            double velocityresult = 1.0;
            if (iswoman) {
                switch (womantype) {
                    case 2: {
                        switch (Choice) {
                            case 1: {
                                NameOfWalk = "WGOThin002";
                                velocityresult = 1.0;
                                break;
                            }
                            case 2: {
                                NameOfWalk = "WGOThin004";
                                velocityresult = 0.8;
                                break;
                            }
                            case 3: {
                                NameOfWalk = "WGOThin005";
                                velocityresult = 1.2;
                                break;
                            }
                            case 4: {
                                NameOfWalk = "WGOThin006";
                                velocityresult = 1.0;
                                break;
                            }
                            default: {
                                NameOfWalk = "WGOThin006";
                                velocityresult = 1.0;
                            }
                        }
                        NameOfRun = "WomanRun001";
                        break;
                    }
                    case 1: {
                        switch (Choice) {
                            case 1: {
                                NameOfWalk = "WGoHell001";
                                velocityresult = 0.8;
                                break;
                            }
                            default: {
                                NameOfWalk = "WGoHell001";
                                velocityresult = 1.0;
                            }
                        }
                        NameOfRun = "WomanRun001";
                        break;
                    }
                    default: {
                        switch (Choice) {
                            case 1: {
                                NameOfWalk = "WGOThin002";
                                velocityresult = 1.0;
                                break;
                            }
                            case 2: {
                                NameOfWalk = "WGOThin004";
                                velocityresult = 0.8;
                                break;
                            }
                            case 3: {
                                NameOfWalk = "WGOThin005";
                                velocityresult = 1.2;
                                break;
                            }
                            case 4: {
                                NameOfWalk = "WGOThin006";
                                velocityresult = 1.0;
                                break;
                            }
                            default: {
                                NameOfWalk = "WGOThin006";
                                velocityresult = 1.0;
                            }
                        }
                        NameOfRun = "WomanRun001";
                        break;
                    }
                }
            } else {
                switch (Choice) {
                    case 1: {
                        NameOfWalk = "ManGo001";
                        velocityresult = 1.2;
                        break;
                    }
                    case 2: {
                        NameOfWalk = "ManGo002";
                        velocityresult = 1.0;
                        break;
                    }
                    case 3: {
                        NameOfWalk = "ManGo003";
                        velocityresult = 1.0;
                        break;
                    }
                    case 4: {
                        NameOfWalk = "ManGo004";
                        velocityresult = 1.0;
                        break;
                    }
                    default: {
                        NameOfWalk = "ManGo004";
                        velocityresult = 1.0;
                    }
                }
                NameOfRun = "ManRun001";
            }
            MANGO1 = SFpedestrians.this.CreateAnm(PERSONAGE, NameOfWalk);
            MANGO1.Tune(tuner, true, true);
            MANGO1.SetVelocity(animation.RandomVelocity(velocityresult) * tuner);
            RUN = SFpedestrians.this.CreateAnm(PERSONAGE, NameOfRun);
            RUN.Tune(tuner * 2.0, true, true);
            RUN.SetVelocity(animation.RandomVelocity(1.5) * tuner * 2.0);
            STANDING = SFpedestrians.this.CreateAnm(PERSONAGE, "StayRandom");
            STANDING.Tune(tuner, true, true);
            STANDING.SetVelocity(0.0);
            JUMP002 = SFpedestrians.this.CreateAnm(PERSONAGE, "Jump002");
            JUMP002.Tune(tuner, false, false);
            temppos.Set(-2.07, 0.0, 0.0);
            JUMP002.SetShift(temppos);
            temppos.Set(1.0, 0.0, 0.0);
            JUMP002.SetDir(temppos);
            JUMP002Sim = SFpedestrians.this.CreateAnm(PERSONAGE, "Jump002Sim");
            JUMP002Sim.Tune(tuner, false, false);
            temppos.Set(2.14, 0.0, 0.0);
            JUMP002Sim.SetShift(temppos);
            temppos.Set(-1.0, 0.0, 0.0);
            JUMP002Sim.SetDir(temppos);
            OTHOD2 = SFpedestrians.this.CreateAnm(PERSONAGE, "Othod002");
            OTHOD2.Tune(tuner, false, false);
            temppos.Set(-0.28849, 0.30839, 0.0);
            OTHOD2.SetShift(temppos);
            temppos.Set(0.0, 1.0, 0.0);
            OTHOD2.SetDir(temppos);
            OTHODB = SFpedestrians.this.CreateAnm(PERSONAGE, "OthodB001");
            OTHODB.Tune(tuner, false, false);
            temppos.Set(0.0, -0.54048, 0.0);
            OTHODB.SetShift(temppos);
            temppos.Set(0.0, 1.0, 0.0);
            OTHODB.SetDir(temppos);
            Falling = SFpedestrians.this.CreateAnm(PERSONAGE, "J_Falling001");
            Falling.Tune(tunerFall, false, false);
            temppos.Set(-2.64, 0.0, 0.0);
            Falling.SetShift(temppos);
            temppos.Set(-1.0, 0.0, 0.0);
            Falling.SetDir(temppos);
            FallingSim = SFpedestrians.this.CreateAnm(PERSONAGE, "J_Falling001Sim");
            FallingSim.Tune(tunerFall, false, false);
            temppos.Set(2.65622, 0.0, 0.0);
            FallingSim.SetShift(temppos);
            temppos.Set(1.0, 0.0, 0.0);
            FallingSim.SetDir(temppos);
            Laing = SFpedestrians.this.CreateAnm(PERSONAGE, "Laying001");
            Laing.Tune(tuner, true, false);
            Laing.SetVelocity(0.0);
            BLaing = SFpedestrians.this.CreateAnm(PERSONAGE, "BLaying001");
            BLaing.Tune(tuner, true, false);
            BLaing.SetVelocity(0.0);
            StandUp = SFpedestrians.this.CreateAnm(PERSONAGE, "StandUp001");
            StandUp.Tune(tunerFall, false, false);
            temppos.Set(0.0, -0.7, 0.0);
            StandUp.SetShift(temppos);
            temppos.Set(0.0, 1.0, 0.0);
            StandUp.SetDir(temppos);
            StandUpBack = SFpedestrians.this.CreateAnm(PERSONAGE, "StandUp002");
            StandUpBack.Tune(tunerFall, false, false);
            temppos.Set(0.62039, 0.91575, 0.0);
            StandUpBack.SetShift(temppos);
            temppos.Set(0.0, -1.0, 0.0);
            StandUpBack.SetDir(temppos);
            FRONTFALL = SFpedestrians.this.CreateAnm(PERSONAGE, "JFFalling001");
            FRONTFALL.Tune(tunerFall, false, false);
            temppos.Set(0.0, 4.09, 0.0);
            FRONTFALL.SetShift(temppos);
            temppos.Set(0.0, 1.0, 0.0);
            FRONTFALL.SetDir(temppos);
            FORCAR1 = SFpedestrians.this.CreateAnm(PERSONAGE, "ForCar001");
            FORCAR1.Tune(tunerFall, false, false);
            temppos.Set(-2.6229, -5.15998, 0.0);
            FORCAR1.SetShift(temppos);
            temppos.Set(-1.0, 0.0, 0.0);
            FORCAR1.SetDir(temppos);
            FORCAR2 = SFpedestrians.this.CreateAnm(PERSONAGE, "ForCar001");
            FORCAR2.Tune(tunerFall, false, false);
            temppos.Set(-2.6229, -5.15998, 0.0);
            FORCAR2.SetShift(temppos);
            temppos.Set(-1.0, 0.0, 0.0);
            FORCAR2.SetDir(temppos);
            FORCAR3 = SFpedestrians.this.CreateAnm(PERSONAGE, "ForCar003");
            FORCAR3.Tune(2.0, false, false);
            temppos.Set(-2.6229, -5.15998, 0.0);
            FORCAR3.SetShift(temppos);
            temppos.Set(-1.0, 0.0, 0.0);
            FORCAR3.SetDir(temppos);
            FORCAR4 = SFpedestrians.this.CreateAnm(PERSONAGE, "ForCar004");
            FORCAR4.Tune(2.0, false, false);
            temppos.Set(-3.43185, -5.15998, 0.0);
            FORCAR4.SetShift(temppos);
            temppos.Set(-1.0, 0.0, 0.0);
            FORCAR4.SetDir(temppos);
            FORCAR5 = SFpedestrians.this.CreateAnm(PERSONAGE, "ForCar005");
            FORCAR5.Tune(2.0, false, false);
            temppos.Set(-3.43185, -5.15998, 0.0);
            FORCAR5.SetShift(temppos);
            temppos.Set(-1.0, 0.0, 0.0);
            FORCAR5.SetDir(temppos);
            FORCAR6 = SFpedestrians.this.CreateAnm(PERSONAGE, "ForCar006");
            FORCAR6.Tune(2.0, false, false);
            temppos.Set(-3.87639, 7.7759, 0.0);
            FORCAR6.SetShift(temppos);
            temppos.Set(0.0, -1.0, 0.0);
            FORCAR6.SetDir(temppos);
            FORCAR7 = SFpedestrians.this.CreateAnm(PERSONAGE, "ForCar007");
            FORCAR7.Tune(2.0, false, false);
            temppos.Set(-3.87639, 7.7759, 0.0);
            FORCAR7.SetShift(temppos);
            temppos.Set(0.0, -1.0, 0.0);
            FORCAR7.SetDir(temppos);
            FORCAR8 = SFpedestrians.this.CreateAnm(PERSONAGE, "ForCar008");
            FORCAR8.Tune(2.0, false, false);
            temppos.Set(-2.9058, 8.6772, 0.0);
            FORCAR8.SetShift(temppos);
            temppos.Set(1.0, 0.0, 0.0);
            FORCAR8.SetDir(temppos);
            FORCAR9 = SFpedestrians.this.CreateAnm(PERSONAGE, "ForCar009");
            FORCAR9.Tune(2.0, false, false);
            temppos.Set(-1.52852, -2.54889, 0.0);
            FORCAR9.SetShift(temppos);
            temppos.Set(-1.0, 0.0, 0.0);
            FORCAR9.SetDir(temppos);
            FORCAR9SIM = SFpedestrians.this.CreateAnm(PERSONAGE, "ForCar009Sim");
            FORCAR9SIM.Tune(2.0, false, false);
            temppos.Set(1.52852, -2.54889, 0.0);
            FORCAR9SIM.SetShift(temppos);
            temppos.Set(1.0, 0.0, 0.0);
            FORCAR9SIM.SetDir(temppos);
            FORCAR10 = SFpedestrians.this.CreateAnm(PERSONAGE, "ForCar010");
            FORCAR10.Tune(2.0, false, false);
            temppos.Set(-2.09063, 2.91445, 0.0);
            FORCAR10.SetShift(temppos);
            temppos.Set(1.0, 0.0, 0.0);
            FORCAR10.SetDir(temppos);
            FORCAR10SIM = SFpedestrians.this.CreateAnm(PERSONAGE, "ForCar010Sim");
            FORCAR10SIM.Tune(2.0, false, false);
            temppos.Set(2.09063, 2.91445, 0.0);
            FORCAR10SIM.SetShift(temppos);
            temppos.Set(-1.0, 0.0, 0.0);
            FORCAR10SIM.SetDir(temppos);
            BACKFALL = SFpedestrians.this.CreateAnm(PERSONAGE, "JBFalling001");
            BACKFALL.Tune(tunerFall, false, false);
            temppos.Set(0.0, -4.85, 0.0);
            BACKFALL.SetShift(temppos);
            temppos.Set(0.0, 1.0, 0.0);
            BACKFALL.SetDir(temppos);
            ToWallB1 = SFpedestrians.this.CreateAnm(PERSONAGE, "To_Wall001");
            ToWallB1.Tune(tuner, false, false);
            temppos.Set(0.0, -0.43108, 0.0);
            ToWallB1.SetShift(temppos);
            temppos.Set(0.0, 1.0, 0.0);
            ToWallB1.SetDir(temppos);
            ToWallB2 = SFpedestrians.this.CreateAnm(PERSONAGE, "To_Wall002");
            ToWallB2.Tune(tuner, false, false);
            temppos.Set(0.0, -0.6009, 0.0);
            ToWallB2.SetShift(temppos);
            temppos.Set(0.0, 1.0, 0.0);
            ToWallB2.SetDir(temppos);
            ToWallF1 = SFpedestrians.this.CreateAnm(PERSONAGE, "To_Wall003");
            ToWallF1.Tune(tuner, false, false);
            temppos.Set(0.0, 0.52884, 0.0);
            ToWallF1.SetShift(temppos);
            temppos.Set(0.0, 1.0, 0.0);
            ToWallF1.SetDir(temppos);
            ATAWALL = SFpedestrians.this.CreateAnm(PERSONAGE, "AtaWall001");
            ATAWALL.Tune(tuner, true, false);
            ATAWALL.SetVelocity(0.0);
            ATAWALLFACE = SFpedestrians.this.CreateAnm(PERSONAGE, "AtaWall002");
            ATAWALLFACE.Tune(tuner, true, false);
            ATAWALLFACE.SetVelocity(0.0);
            ASINCH1 = SFpedestrians.this.CreateAnmanmFive(PERSONAGE, null, null, null, "Head_Act001", "R_H_Act001", "L_H_Act001");
            ASINCH1.Tune(0.5, true, true);
            ASINCH1.SetUpAsinchron(4.0, 0.05);
            PERSONAGE.AddAnimGroup(MANGO1, 3, 1, 5);
            PERSONAGE.AddAnimGroup(RUN, 4, 1, 5);
            PERSONAGE.AddAnimGroup(JUMP002, 1, 3, 5);
            PERSONAGE.AddAnimGroup(JUMP002Sim, 1, 4, 5);
            PERSONAGE.AddAnimGroup(Falling, 1, 6, 5);
            PERSONAGE.AddAnimGroup(FallingSim, 1, 5, 5);
            PERSONAGE.AddAnimGroup(StandUp, 1, 10, 5);
            PERSONAGE.AddAnimGroup(StandUpBack, 1, 9, 5);
            PERSONAGE.AddAnimGroup(FRONTFALL, 1, 7, 5);
            PERSONAGE.AddAnimGroup(BACKFALL, 1, 8, 5);
            PERSONAGE.AddAnimGroup(ToWallB1, 1, 1, 5);
            PERSONAGE.AddAnimGroup(ToWallB2, 1, 1, 5);
            PERSONAGE.AddAnimGroup(ToWallF1, 1, 2, 5);
            PERSONAGE.AddAnimGroup(OTHOD2, 1, 11, 5);
            PERSONAGE.AddAnimGroup(OTHODB, 1, 12, 5);
            PERSONAGE.AddAnimGroup(FORCAR1, 1, 14, 5);
            PERSONAGE.AddAnimGroup(FORCAR2, 1, 14, 5);
            PERSONAGE.AddAnimGroup(FORCAR3, 1, 14, 5);
            PERSONAGE.AddAnimGroup(FORCAR4, 1, 14, 5);
            PERSONAGE.AddAnimGroup(FORCAR5, 1, 14, 5);
            PERSONAGE.AddAnimGroup(FORCAR6, 1, 14, 5);
            PERSONAGE.AddAnimGroup(FORCAR7, 1, 14, 5);
            PERSONAGE.AddAnimGroup(FORCAR8, 1, 14, 5);
            PERSONAGE.AddAnimGroup(FORCAR9, 1, 13, 5);
            PERSONAGE.AddAnimGroup(FORCAR9SIM, 1, 13, 5);
            PERSONAGE.AddAnimGroup(FORCAR10, 1, 14, 5);
            PERSONAGE.AddAnimGroup(FORCAR10SIM, 1, 14, 5);
            PERSONAGE.AddAnimGroup(ATAWALL, 2, 1, 5);
            PERSONAGE.AddAnimGroup(ATAWALLFACE, 2, 2, 5);
            PERSONAGE.AddAnimGroup(Laing, 5, 3, 5);
            PERSONAGE.AddAnimGroup(BLaing, 5, 2, 5);
            PERSONAGE.AddAnimGroup(STANDING, 5, 1, 5);
            PERSONAGE.AddAnimGroup(ASINCH1, 6, 0, 5);
            PERSONAGE.SetFive(1, MANGO1);
            eng.play(PERSONAGE);
        }
    }
}

