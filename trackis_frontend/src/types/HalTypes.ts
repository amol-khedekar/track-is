interface HalLink {
  href: string;
  templated?: boolean;
}

interface HalResource {
  _embedded?: {
    [rel: string]: HalResource | HalResource[];
  };

  _links: { [rel: string]: HalLink | HalLink[] };
}

export type { HalLink, HalResource };
