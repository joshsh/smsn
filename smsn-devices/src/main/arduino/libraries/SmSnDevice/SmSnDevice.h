/*
  SmSnDevice.h - Arduino library for the Semantic Synchrony devices (currently Monitron, Typeatron, and Extend-o-Hand)
  See: http://github.com/joshsh/smsn

  Created by Joshua Shinavier, 2014
  Released into the public domain.
*/

#ifndef SmSnDevice_h
#define SmSnDevice_h

#include "SmSn.h"
#include <OSCBundle.h>
#include <SmSnOsc.h>
//#include <Droidspeak.h>
//#include <Morse.h>   // Morse removed for now due to space constraints w.r.t. Extend-o-Hand on Arduino Nano

const unsigned long infoCueHapticDurationMs = 100;
const unsigned long infoCueVisualDurationMs = 200;
const unsigned long errorCueHapticDurationMs = 100;
const unsigned long errorCueVisualDurationMs = 200;
const unsigned long okCueVisualDurationMs = 100;
const unsigned long readyCueVisualDurationMs = 10000;
const unsigned long warningCueHapticDurationMs = 100;
const unsigned long warningCueVisualDurationMs = 200;

class SmSnDevice
{
  public:
    SmSnDevice(const char *oscPrefix);

    void setup();

    // if set to true (the default), listen for incoming OSC messages
    // if set to false, do not wait for input, permitting a higher sampling and output rate
    void enableInput(boolean b);
    void sendPingReply();
    SmSnOsc *getOSC();
    const char *address(const char *suffix);

    void setLoopTimeHandler(void (*handler)(double));
    double getLoopTime();
    unsigned long beginLoop();

    virtual void setColor(unsigned long color) = 0;
    virtual void vibrate(unsigned long durationMs) = 0;
    virtual void playTone(unsigned int frequency, unsigned long durationMs) = 0;
    void setColorFor(unsigned long color, unsigned long durationMs);
    //Morse *getMorse();
    //Droidspeak *getDroidspeak();

    void errorCue();
    void infoCue();
    void okCue();
    void readyCue();
    void warningCue();

    // ping the host device periodically until a reply is received, then emit an "ok" cue and cease pinging
#ifdef BOUNTIFUL_RAM
    void pingUntilConnected();
    void confirmConnection();
#endif

    const char* getContext();
    void setContext(const char *context);

  protected:
    virtual void setupPins() = 0;
    virtual void setupOther() = 0;
    virtual bool handleOSCBundle(class OSCBundle &bundle) = 0;
    virtual void onBeginLoop(unsigned long now) = 0;
    virtual void onLoopTimeUpdated(double loopTime) = 0;

    //virtual Morse *createMorse() = 0;
    //virtual Droidspeak *createDroidspeak() = 0;

    // settable identifier which is included with each gestural and sensor output
    char contextName[64];

    boolean inputEnabled;
    void sendHeartbeatMessage(unsigned long now);
    void updateSamplingRate();

    unsigned long ledCueLength;
    unsigned long ledCueSince;
    void checkLedStatus(unsigned long now);

    SmSnOsc osc;
    OSCBundle *bundleIn;
    char oscAddressBuffer[50];

    unsigned long lastHeartbeat;

    // for estimation of sampling rate
    double loopTime;
    int loopCount;
    int loopThreshold;
    unsigned long loopStartTime;
    void (*loopTimeHandler)(double);

  private:
    //Morse *morse;
    //Droidspeak *droidspeak;

    void handleOSCBundleInternal(class OSCBundle &bundle);

#ifdef BOUNTIFUL_RAM
    void checkConnection(unsigned long now);
    bool connecting, connected;
    unsigned long lastConnectionAttempt;
#endif
};

#endif // SmSnDevice_h
