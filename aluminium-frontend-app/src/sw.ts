/// <reference lib="WebWorker" />

import { precacheAndRoute } from "workbox-precaching";

const sw = self as unknown as ServiceWorkerGlobalScope & typeof globalThis;
console.log("Service Worker Loaded...");

sw.addEventListener("push", function (event: ExtendableEvent) {
  console.warn("Push Notification Received", event);
  const pushEvent = event as PushEvent;
  if (pushEvent.data) {
    const data = pushEvent.data.json();
    console.log("Push Event data: ", data);
    const options: NotificationOptions = {
      body: data.body,
      icon: "favicon-32x32.png",
      vibrate: [100, 50, 100],
      data: {
        dateOfArrival: Date.now(),
        primaryKey: "2",
      },
      timestamp: Date.now(),
    };
    console.log("Push Event options: ", options);
    console.log("Push Event options: ", JSON.stringify(options));
    console.log("self.registration: ", sw.registration);

    event.waitUntil(sw.registration.showNotification(data.title, options));
    console.log("End of push event listener");
  }
});

precacheAndRoute((self as any).__WB_MANIFEST);
