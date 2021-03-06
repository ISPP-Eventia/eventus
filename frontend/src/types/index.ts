import { RcFile } from "antd/lib/upload";
import { ReactNode } from "react";

// Utility types
export type Coordinates = {
  latitude: number;
  longitude: number;
};

export type UploadFileAxios = {
  file: RcFile;
  onProgress: (progress: { percent: number }) => void;
  onSuccess: (message: string) => void;
  onError: (error: { err: any }) => void;
};

export type Media = {
  id: number;
  path?: string;
  title?: string;
  uploadDate?: string;
};

/* eslint-disable camelcase */
export type PaymentMethod = {
  object: string;
  data: [
    {
      acss_debit: null;
      afterpay_clearpay: null;
      alipay: null;
      au_becs_debit: null;
      bacs_debit: null;
      bancontact: null;
      billing_details: {
        address: {
          city: null;
          country: string;
          line1: null;
          line2: null;
          postal_code: null;
          state: null;
        };
        email: null;
        name: null;
        phone: null;
      };
      boleto: null;
      card: {
        brand: string;
        checks: {
          address_line1_check: null;
          address_postal_code_check: null;
          cvc_check: string;
        };
        country: string;
        description: null;
        exp_month: number;
        exp_year: number;
        fingerprint: string;
        funding: string;
        iin: null;
        issuer: null;
        last4: number;
        networks: {
          available: [string];
          preferred: null;
        };
        three_d_secure_usage: {
          supported: boolean;
        };
        wallet: null;
      };
      card_present: null;
      created: number;
      customer: string;
      eps: null;
      fpx: null;
      giropay: null;
      grabpay: null;
      id: string;
      ideal: null;
      interac_present: null;
      klarna: null;
      konbini: null;
      livemode: boolean;
      metadata: {};
      object: string;
      oxxo: null;
      p24: null;
      paynow: null;
      sepa_debit: null;
      sofort: null;
      type: string;
      us_bank_account: null;
      wechat_pay: null;
    }
  ];
  has_more: boolean;
  url: string;
  request_params: {
    type: string;
  };
};

// Main types
export type User = {
  id?: number;
  firstName: string;
  lastName: string;
  birthDate?: string;
  email?: string;
  password?: string;
};

export type EventUs = {
  id?: number;
  organizer?: User;
  title: string;
  description?: string;
  price: number;
  coordinates?: Coordinates;
  media?: Media[];
  startDate?: string;
  endDate?: string;
  prize?: number;
  rating?: number;
  mediaIds?: string;
  tags?: ITag[];
  tagsIds?: string;
};

export type ITag = {
  id?: number;
  name: string;
};

export type Location = {
  id?: number;
  owner?: User;
  name: string;
  description?: string;
  coordinates: Coordinates;
  price: number;
  media: Media[];
  mediaIds?: string;
};

// Relation types
export type Participation = {
  id?: number;
  buyDate: string;
  ticket: string;
  price: number;
  event: EventUs;
};

export type Sponsorship = {
  id?: number;
  eventId?: number;
  user?: User;
  name?: String;
  quantity: number;
  isAccepted?: boolean;
  media?: Media[];
  mediaIds?: string;
};

export type Hosting = {
  id?: number;
  eventId: number;
  locationId: number;
  price?: number;
  isAccepted?: boolean;
  event?: EventUs;
};

// Form values types

export type SponsorshipFormValues = {
  quantity: number;
  media: Media[];
};

export type EventFormValues = {
  title: string;
  fromTo: [Date, Date];
  price: number;
  description?: string;
  media: Media[];
  tags: ITag[];
};

export type LocationFormValues = {
  price: number;
  name: string;
  description: string;
  longitude: number;
  latitude: number;
  media: Media[];
};

export type LoginFormValues = {
  email: string;
  password: string;
};

export type SignupFormValues = {
  firstName: string;
  lastName: string;
  birthDate: Date;
  email: string;
  password: string;
};

export type UserFormValues = {
  id?: number;
  firstName: string;
  lastName: string;
  birthDate?: string;
  email?: string;
  enable?: boolean;
};

// Share, social media
export type SocialMedia =
  | "twitter"
  | "facebook"
  | "whatsapp"
  | "telegram"
  | "mail";

// Modal opener
export type Opener = {
  title?: string;
  color?: "primary" | "secondary" | "success";
  disable?: boolean;
  icon: ReactNode;
};
