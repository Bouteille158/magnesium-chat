/// <reference lib="WebWorker" />

console.log("Service Worker Loaded...");

self.addEventListener("push", function (event) {
  const pushEvent = event as PushEvent;
  if (pushEvent.data) {
    const data = pushEvent.data.json();
    const options = {
      body: data.body,
      icon: "images/notification-flat.png",
      vibrate: [100, 50, 100],
      data: {
        dateOfArrival: Date.now(),
        primaryKey: "2",
      },
      actions: [
        {
          action: "explore",
          title: "Go to the site",
          icon: "images/checkmark.png",
        },
        {
          action: "close",
          title: "Close the notification",
          icon: "images/xmark.png",
        },
      ],
    };
    (event as ExtendableEvent).waitUntil(
      (
        self as unknown as ServiceWorkerGlobalScope
      ).registration.showNotification(data.title, options)
    );
  }
});
