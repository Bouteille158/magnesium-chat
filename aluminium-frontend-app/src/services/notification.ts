import { getVapidPublicKey } from "./vapidKeys";

const apiURL = `${import.meta.env.VITE_SODIUM_API_URL}/api`;

export const sendNotification = (
  title: string,
  options?: NotificationOptions
) => {
  if (!("Notification" in window)) {
    console.log("This browser does not support notifications!");
    return;
  }

  if (Notification.permission === "granted") {
    new Notification(title, options);
  }
};

export async function subscribeToPushNotifications() {
  const vapidPublicKey = (await getVapidPublicKey()).trim();
  console.log("vapidPublicKey: ", vapidPublicKey);
  const convertedVapidKey = urlBase64ToUint8Array(vapidPublicKey);
  console.log("convertedVapidKey: ", convertedVapidKey);

  // Check if the service worker is supported
  if ("serviceWorker" in navigator) {
    navigator.serviceWorker.ready
      .then((registration) => {
        if (registration) {
          console.log("Service worker is ready");
        } else {
          console.log("Service worker is not ready");
        }
      })
      .catch((error) => {
        console.error("Error checking service worker readiness:", error);
      });
  } else {
    console.log("Service worker is not supported in this browser");
    return;
  }

  // Check if the notification permission has been denied
  if (Notification.permission !== "granted") {
    console.error("Permission for notifications was not granted");
    return;
  } else {
    console.log("Permission for notifications was granted");
  }

  // Check if the push manager is supported
  if ("PushManager" in window) {
    console.log("Ce navigateur prend en charge les notifications push");
  } else {
    console.log("Ce navigateur ne prend pas en charge les notifications push");
    return;
  }

  let registration;
  try {
    registration = await navigator.serviceWorker.ready;
    console.log("registration: ", registration);
  } catch (err) {
    console.error("Error during service worker registration: ", err);
    return;
  }

  if (!registration) {
    console.error("Service worker is not ready");
    return;
  }

  let subscription;
  try {
    subscription = await registration.pushManager.getSubscription();
    if (!subscription) {
      subscription = await registration.pushManager.subscribe({
        userVisibleOnly: true,
        applicationServerKey: convertedVapidKey,
      });
    }
    console.log("subscription: ", subscription);
  } catch (err) {
    console.error("Error during push manager subscription: ", err);
    return;
  }

  try {
    await fetch(apiURL + "/subscribeToPushNotification", {
      method: "POST",
      body: JSON.stringify(subscription),
      headers: {
        "content-type": "application/json",
      },
    });
  } catch (err) {
    console.error("Error during fetch: ", err);
  }
}

export function urlBase64ToUint8Array(base64String: string) {
  const padding = "=".repeat((4 - (base64String.length % 4)) % 4);
  const base64 = (base64String + padding)
    .replace(/\-/g, "+")
    .replace(/_/g, "/");

  const rawData = window.atob(base64);
  const outputArray = new Uint8Array(rawData.length);

  for (let i = 0; i < rawData.length; ++i) {
    outputArray[i] = rawData.charCodeAt(i);
  }
  return outputArray;
}
