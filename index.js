import { NativeModules } from "react-native";

const { PdfTextExtractor } = NativeModules;

function ensureAndroid() {
  if (Platform.OS !== "android") {
    throw new Error("sms-reader works only on Android");
  }
}

if (!PdfTextExtractor) {
  throw new Error(
    "PdfTextExtractor native module not found. Make sure the package is linked and you rebuilt the app.",
  );
}

export async function isPasswordProtected(file) {
  return PdfTextExtractor.isPasswordProtected(file);
}

export async function extractPdfText(file, password) {
  ensureAndroid();
  file = file.replace("file://", "");
  return PdfTextExtractor.extractText(file, password);
}

export default { isPasswordProtected, extractPdfText };
