declare module "react-native-pdf-text-extractor" {
  export function isPasswordProtected(uri: string): Promise<boolean>;
  export function extractPdfText(
    uri: string,
    password?: string,
  ): Promise<string>;

  const PdfTextExtractor: {
    isPasswordProtected: typeof isPasswordProtected;
    extractPdfText: typeof extractPdfText;
  };

  export default PdfTextExtractor;
}
